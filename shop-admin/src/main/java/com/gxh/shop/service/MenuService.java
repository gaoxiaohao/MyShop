package com.gxh.shop.service;

import com.gxh.shop.dto.MenuNode;
import com.gxh.shop.model.SMenu;

import java.util.List;

/**
 * @author gxh
 */
public interface MenuService {

    /**
     * 创建后台菜单
     */
    int create(SMenu menu);

    /**
     * 修改后台菜单
     */
    int update(Long id, SMenu menu);

    /**
     * 根据ID获取菜单详情
     */
    SMenu getItem(Long id);

    /**
     * 根据ID删除菜单
     */
    int delete(Long id);

    /**
     * 分页查询后台菜单
     */
    List<SMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    List<MenuNode> treeList();

    /**
     * 修改菜单显示状态
     */
    int updateHidden(Long id, Integer hidden);
}
