package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductOperateLog;
import com.gxh.shop.model.PProductOperateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductOperateLogMapper {
    long countByExample(PProductOperateLogExample example);

    int deleteByExample(PProductOperateLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductOperateLog record);

    int insertSelective(PProductOperateLog record);

    List<PProductOperateLog> selectByExample(PProductOperateLogExample example);

    PProductOperateLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductOperateLog record, @Param("example") PProductOperateLogExample example);

    int updateByExample(@Param("record") PProductOperateLog record, @Param("example") PProductOperateLogExample example);

    int updateByPrimaryKeySelective(PProductOperateLog record);

    int updateByPrimaryKey(PProductOperateLog record);
}