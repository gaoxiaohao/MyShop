package com.gxh.shop.dao;

import com.gxh.shop.dto.OrderReturnApplyResult;
import com.gxh.shop.dto.ReturnApplyQueryParam;
import com.gxh.shop.model.OOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface OrderReturnApplyDao {
    /**
     * 查询申请列表
     */
    List<OOrderReturnApply> getList(@Param("queryParam") ReturnApplyQueryParam queryParam);

    /**
     * 获取申请详情
     */
    OrderReturnApplyResult getDetail(@Param("id")Long id);
}
