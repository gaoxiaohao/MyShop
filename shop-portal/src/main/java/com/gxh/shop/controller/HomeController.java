package com.gxh.shop.controller;

import com.gxh.shop.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gxh
 */
@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping("/getMessage")
    public String getMessage(){
        return homeService.getMessage();
    }
}
