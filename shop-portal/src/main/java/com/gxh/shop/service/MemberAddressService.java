package com.gxh.shop.service;

import com.gxh.shop.model.SMemberAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gxh
 */
public interface MemberAddressService {
    /**
     * 添加收货地址
     */
    int add(SMemberAddress address);

    /**
     * 删除收货地址
     * @param id 地址表的id
     */
    int delete(Long id);

    /**
     * 修改收货地址
     * @param id 地址表的id
     * @param address 修改的收货地址信息
     */
    @Transactional
    int update(Long id, SMemberAddress address);

    /**
     * 返回当前用户的收货地址
     */
    List<SMemberAddress> list();

    /**
     * 获取地址详情
     * @param id 地址id
     */
    SMemberAddress getItem(Long id);
}
