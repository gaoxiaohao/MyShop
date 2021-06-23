package com.gxh.shop.service;

import com.gxh.shop.dto.ProductCategoryWithChildrenItem;
import com.gxh.shop.model.PProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gxh
 */
public interface ProductCategoryService {

    /**
     * 创建商品分类
     */
    @Transactional
    int create(PProductCategory pProductCategory);

    /**
     * 修改商品分类
     */
    @Transactional
    int update(Long id, PProductCategory pProductCategory);

    /**
     * 分页获取商品分类
     */
    List<PProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 删除商品分类
     */
    int delete(Long id);

    /**
     * 根据ID获取商品分类
     */
    PProductCategory getItem(Long id);

    /**
     * 批量修改导航状态
     */
    int updateNavStatus(List<Long> ids, Integer navStatus);

    /**
     * 批量修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 以层级形式获取商品分类
     */
    List<ProductCategoryWithChildrenItem> listWithChildren();
}
