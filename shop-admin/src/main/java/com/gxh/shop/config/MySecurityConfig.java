package com.gxh.shop.config;

import com.gxh.shop.model.SResource;
import com.gxh.shop.security.component.DynamicSecurityService;
import com.gxh.shop.security.config.SecurityConfig;
import com.gxh.shop.service.AdminService;
import com.gxh.shop.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author gxh
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends SecurityConfig {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ResourceService resourceService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<SResource> resourceList = resourceService.listAll();
            for (SResource resource : resourceList) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }
}
