package com.gxh.shop.controller;


import com.gxh.shop.mapper.SAdminMapper;
import com.gxh.shop.model.PProduct;
import com.gxh.shop.model.SAdmin;
import com.gxh.shop.security.util.JwtTokenUtil;
import com.gxh.shop.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxh
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private TestService testService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @RequestMapping(value = "listAll",method = RequestMethod.GET)
    public List<PProduct> listAll(){
        return testService.listAll();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map login( @RequestParam String username,@RequestParam String password) {
        String token = testService.login(username, password);
        if (token == null) {
            log.error("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return tokenMap;
    }

}
