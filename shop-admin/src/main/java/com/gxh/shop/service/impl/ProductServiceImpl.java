package com.gxh.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.gxh.shop.dao.ProductAttributeValueDao;
import com.gxh.shop.dao.ProductVerifyDad;
import com.gxh.shop.dao.SkuStockDao;
import com.gxh.shop.dto.ProductParam;
import com.gxh.shop.dto.ProductQueryParam;
import com.gxh.shop.mapper.PProductAttributeValueMapper;
import com.gxh.shop.mapper.PProductMapper;
import com.gxh.shop.mapper.PSkuStockMapper;
import com.gxh.shop.model.*;
import com.gxh.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gxh
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private PSkuStockMapper skuStockMapper;
    @Autowired
    private PProductMapper productMapper;
    @Autowired
    private SkuStockDao skuStockDao;
    @Autowired
    private ProductAttributeValueDao productAttributeValueDao;
    @Autowired
    private PProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    private ProductVerifyDad productVertifyDao;

    @Override
    public int create(ProductParam productParam) {
        //创建商品
        productParam.setId(null);
        productMapper.insertSelective(productParam);
        //根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = productParam.getId();
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(), productId);
        //添加sku库存信息
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), productId);
        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
        return 1;
    }

    private void handleSkuStockCode(List<PSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) {
            return;
        }
        for (int i = 0; i < skuStockList.size(); i++) {
            PSkuStock skuStock = skuStockList.get(i);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

    /**
     * 利用反射建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) {
                return;
            }
            //单个
            for (Object item : dataList) {
                //获取setId方法
                Method setId = item.getClass().getMethod("setId", Long.class);
                //设置单个对象id值为空
                setId.invoke(item, (Long) null);
                //获取单个对象商品值为上级返回的值
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                //设置单个对象的商品id
                setProductId.invoke(item, productId);
            }
            //获取dao层中insertList方法
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            //设置insertList方法啊参数是dataList
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(Long id, ProductParam productParam) {
        //更新商品信息
        productParam.setId(id);
        productMapper.updateByPrimaryKeySelective(productParam);

        //修改sku库存信息
        handleUpdateSkuStockList(id, productParam);
        //修改商品参数,添加自定义商品规格
        PProductAttributeValueExample productAttributeValueExample = new PProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), id);
        return 1;
    }


    private void handleUpdateSkuStockList(Long id, ProductParam productParam) {
        //当前的sku信息
        List<PSkuStock> currSkuList = productParam.getSkuStockList();
        //当前没有sku直接删除
        if (CollUtil.isEmpty(currSkuList)) {
            PSkuStockExample skuStockExample = new PSkuStockExample();
            skuStockExample.createCriteria().andProductIdEqualTo(id);
            skuStockMapper.deleteByExample(skuStockExample);
            return;
        }
        //获取初始sku信息
        PSkuStockExample skuStockExample = new PSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        List<PSkuStock> oriStuList = skuStockMapper.selectByExample(skuStockExample);
        //获取新增sku信息
        List<PSkuStock> insertSkuList = currSkuList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<PSkuStock> updateSkuList = currSkuList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(PSkuStock::getId).collect(Collectors.toList());
        //获取需要删除的sku信息
        List<PSkuStock> removeSkuList = oriStuList.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        //新增sku
        if (CollUtil.isNotEmpty(insertSkuList)) {
            relateAndInsertList(skuStockDao, insertSkuList, id);
        }
        //删除sku
        if (CollUtil.isNotEmpty(removeSkuList)) {
            List<Long> removeSkuIds = removeSkuList.stream().map(PSkuStock::getId).collect(Collectors.toList());
            PSkuStockExample removeExample = new PSkuStockExample();
            removeExample.createCriteria().andIdIn(removeSkuIds);
            skuStockMapper.deleteByExample(removeExample);
        }
        //修改sku
        if (CollUtil.isNotEmpty(updateSkuList)) {
            for (PSkuStock pmsSkuStock : updateSkuList) {
                skuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }
        }

    }

    @Override
    public List<PProduct> list(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PProductExample productExample = new PProductExample();
        PProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        return productMapper.selectByExample(productExample);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PProduct product = new PProduct();
        product.setVerifyStatus(verifyStatus);
        PProductExample example = new PProductExample();
        example.createCriteria().andIdIn(ids);
        List<PProductVertify> list = new ArrayList<>();
        int count = productMapper.updateByExampleSelective(product, example);
        //修改完审核状态后插入审核记录
        for (Long id : ids) {
            PProductVertify record = new PProductVertify();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        productVertifyDao.insertList(list);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PProduct record = new PProduct();
        record.setPublishStatus(publishStatus);
        PProductExample example = new PProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PProduct record = new PProduct();
        record.setRecommandStatus(recommendStatus);
        PProductExample example = new PProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PProduct record = new PProduct();
        record.setNewStatus(newStatus);
        PProductExample example = new PProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PProduct record = new PProduct();
        record.setDeleteStatus(deleteStatus);
        PProductExample example = new PProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<PProduct> list(String keyword) {
        PProductExample productExample = new PProductExample();
        PProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if(!StringUtils.isEmpty(keyword)){
            criteria.andNameLike("%" + keyword + "%");
            productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(productExample);
    }
}
