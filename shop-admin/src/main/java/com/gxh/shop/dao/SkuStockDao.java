package com.gxh.shop.dao;

import com.gxh.shop.model.PSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface SkuStockDao {
    /**
     * 批量插入操作
     */
    int insertList(@Param("list") List<PSkuStock> skuStockList);

    /**
     * 批量插入或替换操作
     */
    int replaceList(@Param("list")List<PSkuStock> skuStockList);
}
