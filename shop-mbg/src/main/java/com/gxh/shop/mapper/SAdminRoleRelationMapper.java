package com.gxh.shop.mapper;

import com.gxh.shop.model.SAdminRoleRelation;
import com.gxh.shop.model.SAdminRoleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SAdminRoleRelationMapper {
    long countByExample(SAdminRoleRelationExample example);

    int deleteByExample(SAdminRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SAdminRoleRelation record);

    int insertSelective(SAdminRoleRelation record);

    List<SAdminRoleRelation> selectByExample(SAdminRoleRelationExample example);

    SAdminRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SAdminRoleRelation record, @Param("example") SAdminRoleRelationExample example);

    int updateByExample(@Param("record") SAdminRoleRelation record, @Param("example") SAdminRoleRelationExample example);

    int updateByPrimaryKeySelective(SAdminRoleRelation record);

    int updateByPrimaryKey(SAdminRoleRelation record);
}