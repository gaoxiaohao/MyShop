package com.gxh.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author gxh
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
    @NotEmpty
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
