package com.gxh.shop.mapper;

import com.gxh.shop.model.SAdminLoginLog;
import com.gxh.shop.model.SAdminLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SAdminLoginLogMapper {
    long countByExample(SAdminLoginLogExample example);

    int deleteByExample(SAdminLoginLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SAdminLoginLog record);

    int insertSelective(SAdminLoginLog record);

    List<SAdminLoginLog> selectByExample(SAdminLoginLogExample example);

    SAdminLoginLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SAdminLoginLog record, @Param("example") SAdminLoginLogExample example);

    int updateByExample(@Param("record") SAdminLoginLog record, @Param("example") SAdminLoginLogExample example);

    int updateByPrimaryKeySelective(SAdminLoginLog record);

    int updateByPrimaryKey(SAdminLoginLog record);
}