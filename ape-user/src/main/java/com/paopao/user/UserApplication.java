package com.paopao.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
/**
 * @Author 111
 * @Date 2022/8/10 16:36
 * @Description
 *
 */
@SpringBootApplication(scanBasePackages = {"com.paopao.user","com.paopao"})
@MapperScan(basePackages = "com.paopao.user.mapper")
@EnableCaching
public class UserApplication {

    public static void main(String[] args) {
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(UserApplication.class);
    }
}
