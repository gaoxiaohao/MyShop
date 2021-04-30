package com.gxh.shop.config;

import com.gxh.shop.security.config.SecurityConfig;

import com.gxh.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 *
 * @author gxh
 */
@Configuration
@EnableWebSecurity
public class MemberSecurityConfig extends SecurityConfig {

    @Autowired
    private MemberService memberService;


    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> memberService.loadUserByUsername(username);
    }

}
