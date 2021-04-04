package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductVertify;
import com.gxh.shop.model.PProductVertifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductVertifyMapper {
    long countByExample(PProductVertifyExample example);

    int deleteByExample(PProductVertifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductVertify record);

    int insertSelective(PProductVertify record);

    List<PProductVertify> selectByExample(PProductVertifyExample example);

    PProductVertify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductVertify record, @Param("example") PProductVertifyExample example);

    int updateByExample(@Param("record") PProductVertify record, @Param("example") PProductVertifyExample example);

    int updateByPrimaryKeySelective(PProductVertify record);

    int updateByPrimaryKey(PProductVertify record);
}