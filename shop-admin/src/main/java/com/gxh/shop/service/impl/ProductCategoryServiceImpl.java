package com.gxh.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxh.shop.dao.ProductCategoryDao;
import com.gxh.shop.dto.ProductCategoryWithChildrenItem;
import com.gxh.shop.mapper.PProductCategoryAttributeMapper;
import com.gxh.shop.mapper.PProductCategoryMapper;
import com.gxh.shop.mapper.PProductMapper;
import com.gxh.shop.model.PProductCategory;
import com.gxh.shop.model.PProductCategoryExample;
import com.gxh.shop.service.ProductCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private PProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public int create(PProductCategory pProductCategory) {
        //没有父分类时为一级分类
        setCategoryLevel(pProductCategory);
        return productCategoryMapper.insertSelective(pProductCategory);
    }

    @Override
    public int update(Long id, PProductCategory pProductCategory) {
        return 0;
    }

    @Override
    public List<PProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PProductCategoryExample example = new PProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return productCategoryMapper.selectByExample(example);    }

    @Override
    public int delete(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PProductCategory getItem(Long id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        PProductCategory productCategory = new PProductCategory();
        productCategory.setNavStatus(navStatus);
        PProductCategoryExample example = new PProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return productCategoryMapper.updateByExampleSelective(productCategory, example);    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PProductCategory productCategory = new PProductCategory();
        productCategory.setShowStatus(showStatus);
        PProductCategoryExample example = new PProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return productCategoryMapper.updateByExampleSelective(productCategory, example);    }

    @Override
    public List<ProductCategoryWithChildrenItem> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }

    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(PProductCategory productCategory) {
        //没有父分类时为一级分类
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            //有父分类时选择根据父分类level设置
            PProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(productCategory.getParentId());
            if (parentCategory != null) {
                productCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }
}
