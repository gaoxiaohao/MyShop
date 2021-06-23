package com.gxh.shop.dto;


import com.gxh.shop.model.PProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *   父子节点
 */
public class ProductCategoryWithChildrenItem  extends PProductCategory {
    @Getter
    @Setter
    @ApiModelProperty("子级分类")
    private List<PProductCategory> children;
}
