package com.gxh.shop.mapper;

import com.gxh.shop.model.SMenu;
import com.gxh.shop.model.SMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMenuMapper {
    long countByExample(SMenuExample example);

    int deleteByExample(SMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SMenu record);

    int insertSelective(SMenu record);

    List<SMenu> selectByExample(SMenuExample example);

    SMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SMenu record, @Param("example") SMenuExample example);

    int updateByExample(@Param("record") SMenu record, @Param("example") SMenuExample example);

    int updateByPrimaryKeySelective(SMenu record);

    int updateByPrimaryKey(SMenu record);
}