package com.gxh.shop.dao;


import com.gxh.shop.model.SResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface AdminRoleDao {

    /**
     * 获取用户所有可访问资源
     */
    List<SResource> getResourceList(@Param("adminId") Long adminId);

}
