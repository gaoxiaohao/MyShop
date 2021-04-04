package com.gxh.shop.mapper;

import com.gxh.shop.model.SMember;
import com.gxh.shop.model.SMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMemberMapper {
    long countByExample(SMemberExample example);

    int deleteByExample(SMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SMember record);

    int insertSelective(SMember record);

    List<SMember> selectByExample(SMemberExample example);

    SMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SMember record, @Param("example") SMemberExample example);

    int updateByExample(@Param("record") SMember record, @Param("example") SMemberExample example);

    int updateByPrimaryKeySelective(SMember record);

    int updateByPrimaryKey(SMember record);
}