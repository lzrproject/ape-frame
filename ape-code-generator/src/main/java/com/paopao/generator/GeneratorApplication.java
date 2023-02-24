package com.paopao.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author paoPao
 * @Date 2023/2/24
 * @Description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
