package com.gxh.shop.service;

import com.gxh.shop.model.PProduct;
import com.gxh.shop.model.SAdmin;
import com.gxh.shop.model.SResource;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

/**
 * @author gxh
 */

public interface TestService {

    /**
     * 获取所有商品
     * @return list
     * */
    List<PProduct> listAll();

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     * */
    String login(String username,String password);
    /**
     * 获取指定用户的可访问资源
     */
    List<SResource> getResourceList(Long adminId);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据用户名获取后台管理员
     */
    SAdmin getAdminByUsername(String username);

}
