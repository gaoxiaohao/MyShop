package com.gxh.shop.mapper;

import com.gxh.shop.model.SRoleResource;
import com.gxh.shop.model.SRoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SRoleResourceMapper {
    long countByExample(SRoleResourceExample example);

    int deleteByExample(SRoleResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SRoleResource record);

    int insertSelective(SRoleResource record);

    List<SRoleResource> selectByExample(SRoleResourceExample example);

    SRoleResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SRoleResource record, @Param("example") SRoleResourceExample example);

    int updateByExample(@Param("record") SRoleResource record, @Param("example") SRoleResourceExample example);

    int updateByPrimaryKeySelective(SRoleResource record);

    int updateByPrimaryKey(SRoleResource record);
}