package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 表明是配置类
 */

import com.example.demo.dao.ServiceDao;
@Configuration
public class config {
    @Bean
    public ServiceDao serviceDao(){
        /** 测试*/
        System.out.println("Hello !");
        return new ServiceDao();
    }
}
