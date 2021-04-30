package com.gxh.shop.service.impl;

import com.gxh.shop.mapper.OOrderSettingMapper;
import com.gxh.shop.model.OOrderSetting;
import com.gxh.shop.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gxh
 */
@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OOrderSettingMapper orderSettingMapper;

    @Override
    public OOrderSetting getItem(Long id) {
        return orderSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OOrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingMapper.updateByPrimaryKey(orderSetting);
    }
}
