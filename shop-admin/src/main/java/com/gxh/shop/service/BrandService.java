package com.gxh.shop.service;

import com.gxh.shop.model.PBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BrandService {
    /**
     * 获取所有品牌
     */
    List<PBrand> listAllBrand();

    /**
     * 创建品牌
     */
    int createBrand(PBrand brand);

    /**
     * 修改品牌
     */
    @Transactional
    int updateBrand(Long id, PBrand brand);

    /**
     * 删除品牌
     */
    int deleteBrand(Long id);

    /**
     * 批量删除品牌
     */
    int deleteBrand(List<Long> ids);

    /**
     * 分页查询品牌
     */
    List<PBrand> listBrand(String keyword, int pageNum, int pageSize);

    /**
     * 获取品牌
     */
    PBrand getBrand(Long id);

    /**
     * 修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);
}
