package com.gxh.shop.mapper;

import com.gxh.shop.model.SMemberLevel;
import com.gxh.shop.model.SMemberLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMemberLevelMapper {
    long countByExample(SMemberLevelExample example);

    int deleteByExample(SMemberLevelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SMemberLevel record);

    int insertSelective(SMemberLevel record);

    List<SMemberLevel> selectByExample(SMemberLevelExample example);

    SMemberLevel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SMemberLevel record, @Param("example") SMemberLevelExample example);

    int updateByExample(@Param("record") SMemberLevel record, @Param("example") SMemberLevelExample example);

    int updateByPrimaryKeySelective(SMemberLevel record);

    int updateByPrimaryKey(SMemberLevel record);
}