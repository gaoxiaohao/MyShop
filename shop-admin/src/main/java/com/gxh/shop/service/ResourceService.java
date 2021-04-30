package com.gxh.shop.service;

import com.gxh.shop.model.SResource;

import java.util.List;

/**
 * @author gxh
 */
public interface ResourceService {

    List<SResource> listAll();
    /**
     * 添加资源
     */
    int create(SResource resource);

    /**
     * 修改资源
     */
    int update(Long id, SResource resource);

    /**
     * 获取资源详情
     */
    SResource getItem(Long id);

    /**
     * 删除资源
     */
    int delete(Long id);

    /**
     * 分页查询资源
     */
    List<SResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

}
