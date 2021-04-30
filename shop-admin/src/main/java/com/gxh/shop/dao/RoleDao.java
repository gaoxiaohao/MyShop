package com.gxh.shop.dao;

import com.gxh.shop.model.SMenu;
import com.gxh.shop.model.SResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface RoleDao {
    /**
     * 根据后台用户ID获取菜单
     */
    List<SMenu> getMenuList(@Param("adminId") Long adminId);
    /**
     * 根据角色ID获取菜单
     */
    List<SMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据角色ID获取资源
     */
    List<SResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
