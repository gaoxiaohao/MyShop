package com.gxh.shop.service;


import com.gxh.shop.model.SResourceCategory;

import java.util.List;

/**
 * @author gxh
 */
public interface ResourceCategoryService {

    /**
     * 获取所有资源分类
     */
    List<SResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    int create(SResourceCategory resourceCategory);

    /**
     * 修改资源分类
     */
    int update(Long id, SResourceCategory resourceCategory);

    /**
     * 删除资源分类
     */
    int delete(Long id);
}
