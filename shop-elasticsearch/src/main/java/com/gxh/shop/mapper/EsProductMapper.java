package com.gxh.shop.mapper;

import com.gxh.shop.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */

public interface EsProductMapper {

    /**
     * 获取指定ID的搜索商品
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
