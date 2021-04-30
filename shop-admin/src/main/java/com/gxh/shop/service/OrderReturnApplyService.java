package com.gxh.shop.service;

import com.gxh.shop.dto.OrderReturnApplyResult;
import com.gxh.shop.dto.ReturnApplyQueryParam;
import com.gxh.shop.dto.UpdateStatusParam;
import com.gxh.shop.model.OOrderReturnApply;

import java.util.List;

/**
 * @author gxh
 */
public interface OrderReturnApplyService {

    /**
     * 分页查询申请
     */
    List<OOrderReturnApply> list(ReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 批量删除申请
     */
    int delete(List<Long> ids);

    /**
     * 修改申请状态
     */
    int updateStatus(Long id, UpdateStatusParam statusParam);

    /**
     * 获取指定申请详情
     */
    OrderReturnApplyResult getItem(Long id);
}
