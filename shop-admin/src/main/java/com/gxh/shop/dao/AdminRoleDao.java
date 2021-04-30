package com.gxh.shop.dao;


import com.gxh.shop.model.SAdminRoleRelation;
import com.gxh.shop.model.SResource;
import com.gxh.shop.model.SRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface AdminRoleDao {

    /**
     * 批量插入用户角色关系
     * @param adminRoleRelationList
     * @return
     */
    int insertList(@Param("list") List<SAdminRoleRelation> adminRoleRelationList);

    /**
     * 获取所有用户角色
     * @param adminId
     * @return
     */
    List<SRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 获取用户所有与可访问的资源
     * @param adminId
     * @return
     */
    List<SResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取资源相关用户id列表
     * @param resourceId
     * @return
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
