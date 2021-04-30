package com.gxh.shop.dao;

import com.gxh.shop.dto.OrderDeliveryParam;
import com.gxh.shop.dto.OrderDetail;
import com.gxh.shop.dto.OrderQueryParam;
import com.gxh.shop.model.OOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDao {

    /**
     * 条件查询订单
     */
    List<OOrder> getList(@Param("queryParam") OrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OrderDetail getDetail(@Param("id") Long id);
}
