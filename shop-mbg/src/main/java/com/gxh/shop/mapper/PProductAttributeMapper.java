package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductAttribute;
import com.gxh.shop.model.PProductAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductAttributeMapper {
    long countByExample(PProductAttributeExample example);

    int deleteByExample(PProductAttributeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductAttribute record);

    int insertSelective(PProductAttribute record);

    List<PProductAttribute> selectByExample(PProductAttributeExample example);

    PProductAttribute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductAttribute record, @Param("example") PProductAttributeExample example);

    int updateByExample(@Param("record") PProductAttribute record, @Param("example") PProductAttributeExample example);

    int updateByPrimaryKeySelective(PProductAttribute record);

    int updateByPrimaryKey(PProductAttribute record);
}