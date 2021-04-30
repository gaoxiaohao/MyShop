package com.gxh.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxh.shop.dao.RoleDao;
import com.gxh.shop.mapper.SRoleMapper;
import com.gxh.shop.mapper.SRoleMenuMapper;
import com.gxh.shop.mapper.SRoleResourceMapper;
import com.gxh.shop.model.*;
import com.gxh.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author gxh
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SRoleMapper roleMapper;
    @Autowired
    private SRoleMenuMapper roleMenuRelationMapper;
    @Autowired
    private SRoleResourceMapper roleResourceRelationMapper;
    @Autowired
    private RoleDao roleDao;

    
    @Override
    public int create(SRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int update(Long id, SRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(List<Long> ids) {
        SRoleExample example = new SRoleExample();
        example.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(example);
    }

    @Override
    public List<SRole> list() {
        return roleMapper.selectByExample(new SRoleExample());
    }

    @Override
    public List<SRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        SRoleExample example = new SRoleExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<SMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }

    @Override
    public List<SMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<SResource> listResource(Long roleId) {
        return roleDao.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        SRoleMenuExample example=new SRoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleMenuRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId : menuIds) {
            SRoleMenu relation = new SRoleMenu();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        SRoleResourceExample example=new SRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            SRoleResource relation = new SRoleResource();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        return resourceIds.size();
    }
}
