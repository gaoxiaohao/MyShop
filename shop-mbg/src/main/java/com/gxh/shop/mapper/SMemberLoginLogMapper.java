package com.gxh.shop.mapper;

import com.gxh.shop.model.SMemberLoginLog;
import com.gxh.shop.model.SMemberLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMemberLoginLogMapper {
    long countByExample(SMemberLoginLogExample example);

    int deleteByExample(SMemberLoginLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SMemberLoginLog record);

    int insertSelective(SMemberLoginLog record);

    List<SMemberLoginLog> selectByExample(SMemberLoginLogExample example);

    SMemberLoginLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SMemberLoginLog record, @Param("example") SMemberLoginLogExample example);

    int updateByExample(@Param("record") SMemberLoginLog record, @Param("example") SMemberLoginLogExample example);

    int updateByPrimaryKeySelective(SMemberLoginLog record);

    int updateByPrimaryKey(SMemberLoginLog record);
}