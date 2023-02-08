package com.paopao.share;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author 111
 * @Date 2022/8/30 22:05
 * @Description
 */
@SpringBootApplication(scanBasePackages = {"com.paopao.share"})
public class ShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShareApplication.class,args);
    }
}
