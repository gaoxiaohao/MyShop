package com.gxh.shop.mapper;

import com.gxh.shop.model.PSkuStock;
import com.gxh.shop.model.PSkuStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PSkuStockMapper {
    long countByExample(PSkuStockExample example);

    int deleteByExample(PSkuStockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PSkuStock record);

    int insertSelective(PSkuStock record);

    List<PSkuStock> selectByExample(PSkuStockExample example);

    PSkuStock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PSkuStock record, @Param("example") PSkuStockExample example);

    int updateByExample(@Param("record") PSkuStock record, @Param("example") PSkuStockExample example);

    int updateByPrimaryKeySelective(PSkuStock record);

    int updateByPrimaryKey(PSkuStock record);
}