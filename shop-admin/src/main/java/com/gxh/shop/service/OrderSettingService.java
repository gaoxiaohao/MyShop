package com.gxh.shop.service;

import com.gxh.shop.model.OOrderSetting;

/**
 * @author gxh
 */
public interface OrderSettingService {
    /**
     * 获取指定订单设置
     */
    OOrderSetting getItem(Long id);

    /**
     * 修改指定订单设置
     */
    int update(Long id, OOrderSetting orderSetting);
}
