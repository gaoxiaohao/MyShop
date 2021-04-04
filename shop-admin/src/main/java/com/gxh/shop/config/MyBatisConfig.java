package com.gxh.shop.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author gxh
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.gxh.shop.mapper","com.gxh.shop.dao"})
public class MyBatisConfig {



}
