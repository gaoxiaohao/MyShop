package com.gxh.shop.config;

import com.gxh.shop.constant.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author gxh
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {


    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.gxh.shop.controller")
                .title("shop")
                .description("后台管理API文档")
                .contactName("shop")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
