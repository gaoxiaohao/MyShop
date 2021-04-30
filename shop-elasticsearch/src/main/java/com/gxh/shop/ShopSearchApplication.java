package com.gxh.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gxh
 */
@SpringBootApplication
@MapperScan({"com.gxh.shop.mapper"})
public class ShopSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchApplication.class,args);
    }
}
