package com.gxh.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxh.shop.dto.MenuNode;
import com.gxh.shop.mapper.SMenuMapper;
import com.gxh.shop.model.SMenu;
import com.gxh.shop.model.SMenuExample;
import com.gxh.shop.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gxh
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {


    @Autowired
    private SMenuMapper menuMapper;


    @Override
    public int create(SMenu menu) {
        menu.setCreateTime(new Date());
        updateLevel(menu);
        return menuMapper.insert(menu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(SMenu menu) {
        if (menu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            menu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            SMenu parentMenu = menuMapper.selectByPrimaryKey(menu.getParentId());
            if (parentMenu != null) {
                menu.setLevel(parentMenu.getLevel() + 1);
            } else {
                menu.setLevel(0);
            }
        }
    }

    @Override
    public int update(Long id, SMenu menu) {
        menu.setId(id);
        updateLevel(menu);
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public SMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        SMenuExample example = new SMenuExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<MenuNode> treeList() {
        List<SMenu> menuList = menuMapper.selectByExample(new SMenuExample());
        List<MenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        SMenu umsMenu = new SMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private MenuNode covertMenuNode(SMenu menu, List<SMenu> menuList) {
        MenuNode node = new MenuNode();
        BeanUtils.copyProperties(menu, node);
        List<MenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
