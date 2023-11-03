package com.paopao.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/21
 */
@SpringBootApplication(scanBasePackages = {"com.paopao"})
@MapperScan("com.paopao.demo.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
