package com.gxh.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.gxh.shop.dao.AdminRoleDao;
import com.gxh.shop.detail.AdminUserDetails;
import com.gxh.shop.dto.UpdateAdminPasswordParam;
import com.gxh.shop.mapper.SAdminLoginLogMapper;
import com.gxh.shop.mapper.SAdminMapper;
import com.gxh.shop.mapper.SAdminRoleRelationMapper;
import com.gxh.shop.model.*;
import com.gxh.shop.security.util.JwtTokenUtil;
import com.gxh.shop.service.AdminService;
import com.gxh.shop.service.RedisService;
import com.gxh.shop.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gxh
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SAdminMapper adminMapper;
    @Autowired
    private SAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private AdminRoleDao adminRoleRelationDao;
    @Autowired
    private SAdminLoginLogMapper loginLogMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public SAdmin getAdminByUsername(String username) {
        SAdminExample adminExample = new SAdminExample();
        adminExample.createCriteria().andUsernameEqualTo(username);
        List<SAdmin> list = adminMapper.selectByExample(adminExample);
        return list.get(0);
    }

    @Override
    public SAdmin register(SAdmin admin) {
        admin.setStatus(1);
        admin.setCreateTime(new Date());
        //判断是否重名
        SAdminExample adminExample = new SAdminExample();
        adminExample.createCriteria().andUsernameEqualTo(admin.getUsername());
        List<SAdmin> list = adminMapper.selectByExample(adminExample);
        if (list.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);
        adminMapper.insert(admin);
        return admin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return "密码不正确";
            }
            if (!userDetails.isEnabled()) {
                return "帐号已被禁用";
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            //修改用户登录时间
            updateLoginTimeByUsername(username);
            //设置用户登录信息
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public SAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<SAdmin> o = (List<SAdmin>) redisService.get("admin:list");
        if (o==null){
            SAdminExample example = new SAdminExample();
            SAdminExample.Criteria criteria = example.createCriteria();
            if (!StringUtils.isEmpty(keyword)) {
                criteria.andUsernameLike("%" + keyword + "%");
                example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
            }
            List<SAdmin> list = adminMapper.selectByExample(example);
            redisService.set("admin:list",list);
        }
        return o;
    }

    @Override
    public int update(Long id, SAdmin admin) {
        admin.setId(id);
        SAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
        if (rawAdmin.getPassword().equals(admin.getPassword())) {
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(admin.getPassword())) {
                admin.setPassword(null);
            } else {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int delete(Long id) {

        return adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        SAdminRoleRelationExample adminRoleRelationExample = new SAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                SAdminRoleRelation roleRelation = new SAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationDao.insertList(list);
        }
        return count;
    }

    @Override
    public List<SRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<SResource> getResourceList(Long adminId) {
        List<SResource> resourceList = null;
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        //-1 未输入数据
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        //-2 根据用户名查不到这个用户
        SAdminExample example = new SAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<SAdmin> adminList = adminMapper.selectByExample(example);
        if (CollUtil.isEmpty(adminList)) {
            return -2;
        }
        //新旧密码不匹配
        SAdmin admin = adminList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), admin.getPassword())) {
            return -3;
        }
        admin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminMapper.updateByPrimaryKey(admin);
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        SAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<SResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }


    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        SAdmin record = new SAdmin();
        record.setLoginTime(new Date());
        SAdminExample example = new SAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(record, example);
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        SAdmin admin = getAdminByUsername(username);
        if (admin == null) {
            return;
        }
        SAdminLoginLog loginLog = new SAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLogMapper.insert(loginLog);
    }

}
