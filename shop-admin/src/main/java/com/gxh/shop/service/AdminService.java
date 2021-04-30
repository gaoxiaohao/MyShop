package com.gxh.shop.service;

import com.gxh.shop.dto.UpdateAdminPasswordParam;
import com.gxh.shop.model.SAdmin;
import com.gxh.shop.model.SResource;
import com.gxh.shop.model.SRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理Service
 * @author gxh
 */
public interface AdminService {

    /**
     * 根据用户名获取后台管理员
     * @param username
     * @return
     */
    SAdmin getAdminByUsername(String username);

    /**
     * 注册
     * @param admin
     * @return
     */
    SAdmin register(SAdmin admin);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    SAdmin getItem(Long id);

    /**
     * 根据用户名或者昵称分页查询用户
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<SAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     * @param id
     * @param admin
     * @return
     */
    int update(Long id, SAdmin admin);

    /**
     * 删除指定用户
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     * @param adminId
     * @param roleIds
     * @return
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 根据用户id查询所拥有的角色
     * @param adminId
     * @return
     */
    List<SRole> getRoleList(Long adminId);

    /**
     * 获取指定用户的拥有的资源
     * @param adminId
     * @return
     */
    List<SResource> getResourceList(Long adminId);

    /**
     * 修改密码
     * @param param
     * @return
     */
    int updatePassword(UpdateAdminPasswordParam param);

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);


}
