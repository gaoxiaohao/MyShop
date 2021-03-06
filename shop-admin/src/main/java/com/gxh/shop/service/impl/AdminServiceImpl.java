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
        //??????????????????
        SAdminExample adminExample = new SAdminExample();
        adminExample.createCriteria().andUsernameEqualTo(admin.getUsername());
        List<SAdmin> list = adminMapper.selectByExample(adminExample);
        if (list.size() > 0) {
            return null;
        }
        //???????????????????????????
        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);
        adminMapper.insert(admin);
        return admin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //????????????????????????????????????
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return "???????????????";
            }
            if (!userDetails.isEnabled()) {
                return "??????????????????";
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            //????????????????????????
            updateLoginTimeByUsername(username);
            //????????????????????????
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("????????????:{}", e.getMessage());
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
            //??????????????????????????????????????????
            admin.setPassword(null);
        } else {
            //?????????????????????????????????????????????
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
        //????????????????????????
        SAdminRoleRelationExample adminRoleRelationExample = new SAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //???????????????
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
        //-1 ???????????????
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        //-2 ????????????????????????????????????
        SAdminExample example = new SAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<SAdmin> adminList = adminMapper.selectByExample(example);
        if (CollUtil.isEmpty(adminList)) {
            return -2;
        }
        //?????????????????????
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
        //??????????????????
        SAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<SResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("????????????????????????");
    }


    /**
     * ?????????????????????????????????
     */
    private void updateLoginTimeByUsername(String username) {
        SAdmin record = new SAdmin();
        record.setLoginTime(new Date());
        SAdminExample example = new SAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(record, example);
    }

    /**
     * ??????????????????
     *
     * @param username ?????????
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
