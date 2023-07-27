package com.paopao.mode;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: paoPao
 * @Date: 2023/7/27
 * @Description:
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class,DataSourceAutoConfiguration.class})
public class ModeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModeApplication.class, args);
    }
}
