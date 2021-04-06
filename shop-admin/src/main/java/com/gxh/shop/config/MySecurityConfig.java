package com.gxh.shop.config;

import com.gxh.shop.security.config.SecurityConfig;
import com.gxh.shop.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author gxh
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends SecurityConfig {

    @Autowired
    private TestService testService;


    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> testService.loadUserByUsername(username);
    }
}
