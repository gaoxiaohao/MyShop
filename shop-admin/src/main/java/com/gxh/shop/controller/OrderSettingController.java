package com.gxh.shop.controller;


import com.gxh.shop.api.CommonResult;
import com.gxh.shop.model.OOrderSetting;
import com.gxh.shop.service.OrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 订单设置管理Controller
 *
 *
 * @author gxh*/
@Controller
@Api(tags = "OmsOrderSettingController-订单设置管理")
@RequestMapping("/orderSetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    @ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OOrderSetting> getItem(@PathVariable Long id) {
        OOrderSetting orderSetting = orderSettingService.getItem(id);
        return CommonResult.success(orderSetting);
    }

    @ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody OOrderSetting orderSetting) {
        int count = orderSettingService.update(id,orderSetting);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
