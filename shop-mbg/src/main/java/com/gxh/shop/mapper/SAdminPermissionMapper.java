package com.gxh.shop.mapper;

import com.gxh.shop.model.SAdminPermission;
import com.gxh.shop.model.SAdminPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SAdminPermissionMapper {
    long countByExample(SAdminPermissionExample example);

    int deleteByExample(SAdminPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SAdminPermission record);

    int insertSelective(SAdminPermission record);

    List<SAdminPermission> selectByExample(SAdminPermissionExample example);

    SAdminPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SAdminPermission record, @Param("example") SAdminPermissionExample example);

    int updateByExample(@Param("record") SAdminPermission record, @Param("example") SAdminPermissionExample example);

    int updateByPrimaryKeySelective(SAdminPermission record);

    int updateByPrimaryKey(SAdminPermission record);
}