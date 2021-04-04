package com.gxh.shop.mapper;

import com.gxh.shop.model.SRoleMenu;
import com.gxh.shop.model.SRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SRoleMenuMapper {
    long countByExample(SRoleMenuExample example);

    int deleteByExample(SRoleMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SRoleMenu record);

    int insertSelective(SRoleMenu record);

    List<SRoleMenu> selectByExample(SRoleMenuExample example);

    SRoleMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SRoleMenu record, @Param("example") SRoleMenuExample example);

    int updateByExample(@Param("record") SRoleMenu record, @Param("example") SRoleMenuExample example);

    int updateByPrimaryKeySelective(SRoleMenu record);

    int updateByPrimaryKey(SRoleMenu record);
}