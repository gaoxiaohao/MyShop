package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductCategoryAttribute;
import com.gxh.shop.model.PProductCategoryAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductCategoryAttributeMapper {
    long countByExample(PProductCategoryAttributeExample example);

    int deleteByExample(PProductCategoryAttributeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductCategoryAttribute record);

    int insertSelective(PProductCategoryAttribute record);

    List<PProductCategoryAttribute> selectByExample(PProductCategoryAttributeExample example);

    PProductCategoryAttribute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductCategoryAttribute record, @Param("example") PProductCategoryAttributeExample example);

    int updateByExample(@Param("record") PProductCategoryAttribute record, @Param("example") PProductCategoryAttributeExample example);

    int updateByPrimaryKeySelective(PProductCategoryAttribute record);

    int updateByPrimaryKey(PProductCategoryAttribute record);
}