package com.gxh.shop.dto;

import com.gxh.shop.model.OCompanyAddress;
import com.gxh.shop.model.OOrderReturnApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gxh
 */
public class OrderReturnApplyResult extends OOrderReturnApply {

    @Getter
    @Setter
    @ApiModelProperty(value = "公司收货地址")
    private OCompanyAddress companyAddress;
}
