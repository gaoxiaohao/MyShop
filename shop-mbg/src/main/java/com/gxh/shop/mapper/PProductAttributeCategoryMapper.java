package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductAttributeCategory;
import com.gxh.shop.model.PProductAttributeCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductAttributeCategoryMapper {
    long countByExample(PProductAttributeCategoryExample example);

    int deleteByExample(PProductAttributeCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductAttributeCategory record);

    int insertSelective(PProductAttributeCategory record);

    List<PProductAttributeCategory> selectByExample(PProductAttributeCategoryExample example);

    PProductAttributeCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductAttributeCategory record, @Param("example") PProductAttributeCategoryExample example);

    int updateByExample(@Param("record") PProductAttributeCategory record, @Param("example") PProductAttributeCategoryExample example);

    int updateByPrimaryKeySelective(PProductAttributeCategory record);

    int updateByPrimaryKey(PProductAttributeCategory record);
}