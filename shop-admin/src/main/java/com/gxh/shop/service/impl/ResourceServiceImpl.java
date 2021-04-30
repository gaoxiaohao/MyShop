package com.gxh.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.gxh.shop.mapper.SResourceMapper;
import com.gxh.shop.model.SResource;
import com.gxh.shop.model.SResourceExample;
import com.gxh.shop.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author gxh
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private SResourceMapper sResourceMapper;



    @Override
    public List<SResource> listAll() {
        return sResourceMapper.selectByExample(new SResourceExample());
    }

    @Override
    public int create(SResource resource) {
        resource.setCreateTime(new Date());
        return sResourceMapper.insert(resource);
    }

    @Override
    public int update(Long id, SResource resource) {
        resource.setId(id);
        return sResourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public SResource getItem(Long id) {
        return sResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return sResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        SResourceExample example = new SResourceExample();
        SResourceExample.Criteria criteria = example.createCriteria();
        if(categoryId!=null){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            criteria.andNameLike('%'+nameKeyword+'%');
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            criteria.andUrlLike('%'+urlKeyword+'%');
        }
        return sResourceMapper.selectByExample(example);
    }
}
