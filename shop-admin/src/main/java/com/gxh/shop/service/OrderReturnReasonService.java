package com.gxh.shop.service;

import com.gxh.shop.model.OOrderReturnReason;

import java.util.List;

/**
 * @author gxh
 */
public interface OrderReturnReasonService {

    /**
     * 添加订单原因
     */
    int create(OOrderReturnReason returnReason);

    /**
     * 修改退货原因
     */
    int update(Long id, OOrderReturnReason returnReason);

    /**
     * 批量删除退货原因
     */
    int delete(List<Long> ids);

    /**
     * 分页获取退货原因
     */
    List<OOrderReturnReason> list(Integer pageSize, Integer pageNum);

    /**
     * 批量修改退货原因状态
     */
    int updateStatus(List<Long> ids, Integer status);

    /**
     * 获取单个退货原因详情信息
     */
    OOrderReturnReason getItem(Long id);
}
