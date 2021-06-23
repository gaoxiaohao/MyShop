package com.gxh.shop.dao;

import com.gxh.shop.dto.ProductCategoryWithChildrenItem;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 获取商品分类及其子分类
     */
    List<ProductCategoryWithChildrenItem> listWithChildren();

}
