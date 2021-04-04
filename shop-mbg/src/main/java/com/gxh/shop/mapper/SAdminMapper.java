package com.gxh.shop.mapper;

import com.gxh.shop.model.SAdmin;
import com.gxh.shop.model.SAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SAdminMapper {
    long countByExample(SAdminExample example);

    int deleteByExample(SAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SAdmin record);

    int insertSelective(SAdmin record);

    List<SAdmin> selectByExample(SAdminExample example);

    SAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SAdmin record, @Param("example") SAdminExample example);

    int updateByExample(@Param("record") SAdmin record, @Param("example") SAdminExample example);

    int updateByPrimaryKeySelective(SAdmin record);

    int updateByPrimaryKey(SAdmin record);
}