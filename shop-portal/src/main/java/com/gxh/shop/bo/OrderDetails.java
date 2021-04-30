package com.gxh.shop.bo;

import com.gxh.shop.model.OOrder;
import com.gxh.shop.model.OOrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetails extends OOrder {
    private List<OOrderItem> orderItemList;
}
