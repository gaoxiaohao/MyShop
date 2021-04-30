package com.gxh.shop.dto;

import com.gxh.shop.model.PProduct;
import com.gxh.shop.model.PProductAttributeValue;
import com.gxh.shop.model.PSkuStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author gxh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductParam extends PProduct {

    @ApiModelProperty("商品的sku库存信息")
    private List<PSkuStock> skuStockList;
    @ApiModelProperty("商品参数及自定义规格属性")
    private List<PProductAttributeValue> productAttributeValueList;
}
