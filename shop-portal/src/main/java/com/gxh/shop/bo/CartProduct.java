package com.gxh.shop.bo;

import com.gxh.shop.model.PProduct;
import com.gxh.shop.model.PProductAttribute;
import com.gxh.shop.model.PSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gxh
 */
@Getter
@Setter
public class CartProduct extends PProduct {
    private List<PProductAttribute> productAttributeList;
    private List<PSkuStock> skuStockList;

}
