package com.gxh.shop.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 * @author gxh

 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.gxh.shop.mapper","com.gxh.shop.dao"})
public class MyBatisConfig {

}
