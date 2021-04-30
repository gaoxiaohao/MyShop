package com.gxh.shop.dto;

import com.gxh.shop.model.SMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gxh
 */
@Getter
@Setter
public class MenuNode extends SMenu {

    @ApiModelProperty(value = "子级菜单")
    private List<MenuNode> children;
}
