package com.gxh.shop.mapper;

import com.gxh.shop.model.SRolePermission;
import com.gxh.shop.model.SRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SRolePermissionMapper {
    long countByExample(SRolePermissionExample example);

    int deleteByExample(SRolePermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SRolePermission record);

    int insertSelective(SRolePermission record);

    List<SRolePermission> selectByExample(SRolePermissionExample example);

    SRolePermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SRolePermission record, @Param("example") SRolePermissionExample example);

    int updateByExample(@Param("record") SRolePermission record, @Param("example") SRolePermissionExample example);

    int updateByPrimaryKeySelective(SRolePermission record);

    int updateByPrimaryKey(SRolePermission record);
}