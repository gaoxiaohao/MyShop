package com.gxh.shop.service.impl;

import com.gxh.shop.mapper.SResourceCategoryMapper;
import com.gxh.shop.model.SResourceCategory;
import com.gxh.shop.model.SResourceCategoryExample;
import com.gxh.shop.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {

    @Autowired
    private SResourceCategoryMapper resourceCategoryMapper;

    @Override
    public List<SResourceCategory> listAll() {
        SResourceCategoryExample resourceCategoryExample = new SResourceCategoryExample();
        resourceCategoryExample.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(resourceCategoryExample);
    }

    @Override
    public int create(SResourceCategory resourceCategory) {
        resourceCategory.setCreateTime(new Date());
        return resourceCategoryMapper.insert(resourceCategory);
    }

    @Override
    public int update(Long id, SResourceCategory resourceCategory) {
        resourceCategory.setId(id);
        return resourceCategoryMapper.updateByPrimaryKeySelective(resourceCategory);
    }

    @Override
    public int delete(Long id) {
        return resourceCategoryMapper.deleteByPrimaryKey(id);
    }
}
