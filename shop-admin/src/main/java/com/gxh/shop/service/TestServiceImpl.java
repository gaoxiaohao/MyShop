package com.gxh.shop.service;


import com.gxh.shop.dao.AdminRoleDao;
import com.gxh.shop.detail.AdminUserDetails;
import com.gxh.shop.mapper.PProductMapper;
import com.gxh.shop.mapper.SAdminMapper;
import com.gxh.shop.model.*;
import com.gxh.shop.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.naming.AuthenticationException;
import java.util.List;

/**
 * @author gxh
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private PProductMapper pProductMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SAdminMapper adminMapper;
    @Autowired
    private AdminRoleDao adminRoleDao;
    @Override
    public List<PProduct> listAll() {
        List<PProduct> pProducts = pProductMapper.selectByExample(new PProductExample());
        return pProducts;
    }


    @Override
    public String login(String username, String password) {
        String token;
        UserDetails userDetails =loadUserByUsername(username);
//        if (passwordEncoder.matches(password,userDetails.getPassword())){
//            log.warn("用户密码不对");
//        }
        if (!userDetails.isEnabled()){
            log.warn("账号被禁用");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public List<SResource> getResourceList(Long adminId) {
        List<SResource> resourceList = adminRoleDao.getResourceList(adminId);
        return resourceList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SAdmin admin = getAdminByUsername(username);
        if (admin == null){
            return null;
        }
        List<SResource> resourceList = getResourceList(admin.getId());
        return new AdminUserDetails(admin,resourceList);
    }

    @Override
    public SAdmin getAdminByUsername(String username) {
        SAdmin admin;
        SAdminExample example = new SAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            return admin;
        }
        return null;
    }
}
