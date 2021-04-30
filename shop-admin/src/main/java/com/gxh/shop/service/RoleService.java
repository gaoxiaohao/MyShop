package com.gxh.shop.service;

import com.gxh.shop.model.SMenu;
import com.gxh.shop.model.SResource;
import com.gxh.shop.model.SRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gxh
 */
public interface RoleService {

    /**
     * 添加角色
     * @param role
     * @return
     */
    int create(SRole role);

    /**
     * 修改角色信息
     * @param id
     * @param role
     * @return
     */
    int update(Long id, SRole role);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    int delete(List<Long> ids);

    /**
     * 获取所有角色列表
     * @return
     */
    List<SRole> list();

    /**
     * 分页获取角色列表
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<SRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员id获取菜单列表
     * @param adminId
     * @return
     */
    List<SMenu> getMenuList(Long adminId);

    /**
     * 获取角色相关菜单
     * @param roleId
     * @return
     */
    List<SMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     * @param roleId
     * @return
     */
    List<SResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     * @param roleId
     * @param resourceIds
     * @return
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
