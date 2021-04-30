package com.gxh.shop.dto;

import com.gxh.shop.model.OOrder;
import com.gxh.shop.model.OOrderItem;
import com.gxh.shop.model.OOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderDetail extends OOrder {
    @Getter
    @Setter
    @ApiModelProperty("订单商品列表")
    private List<OOrderItem> orderItemList;
    @Getter
    @Setter
    @ApiModelProperty("订单操作记录列表")
    private List<OOrderOperateHistory> historyList;
}
