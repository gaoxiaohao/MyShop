package com.gxh.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxh.shop.mapper.PBrandMapper;
import com.gxh.shop.mapper.PProductMapper;
import com.gxh.shop.model.PBrand;
import com.gxh.shop.model.PBrandExample;
import com.gxh.shop.model.PProduct;
import com.gxh.shop.model.PProductExample;
import com.gxh.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author gxh
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private PBrandMapper brandMapper;
    @Autowired
    private PProductMapper productMapper;

    @Override
    public List<PBrand> listAllBrand() {
        return brandMapper.selectByExample(new PBrandExample());
    }

    @Override
    public int createBrand(PBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(Long id, PBrand brand) {
        //更新品牌时要更新商品中的品牌名称
        PProduct product = new PProduct();
        product.setBrandName(brand.getName());
        PProductExample example = new PProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(product,example);
        return brandMapper.updateByPrimaryKeySelective(brand);    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);    }

    @Override
    public int deleteBrand(List<Long> ids) {
        PBrandExample pmsBrandExample = new PBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(pmsBrandExample);    }

    @Override
    public List<PBrand> listBrand(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PBrandExample pmsBrandExample = new PBrandExample();
        pmsBrandExample.setOrderByClause("sort desc");
        PBrandExample.Criteria criteria = pmsBrandExample.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return brandMapper.selectByExample(pmsBrandExample);    }

    @Override
    public PBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PBrand pmsBrand = new PBrand();
        pmsBrand.setShowStatus(showStatus);
        PBrandExample pmsBrandExample = new PBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);    }
}
