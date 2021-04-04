package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductAttributeValue;
import com.gxh.shop.model.PProductAttributeValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductAttributeValueMapper {
    long countByExample(PProductAttributeValueExample example);

    int deleteByExample(PProductAttributeValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductAttributeValue record);

    int insertSelective(PProductAttributeValue record);

    List<PProductAttributeValue> selectByExample(PProductAttributeValueExample example);

    PProductAttributeValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductAttributeValue record, @Param("example") PProductAttributeValueExample example);

    int updateByExample(@Param("record") PProductAttributeValue record, @Param("example") PProductAttributeValueExample example);

    int updateByPrimaryKeySelective(PProductAttributeValue record);

    int updateByPrimaryKey(PProductAttributeValue record);
}