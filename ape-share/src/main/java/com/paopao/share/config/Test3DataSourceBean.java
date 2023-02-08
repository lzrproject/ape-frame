package com.paopao.share.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description
 */
@Configuration
public class Test3DataSourceBean {

    @Value("${spring.datasource.test3.driver-class-name}")
    private String test3Driver;

    @Value("${spring.datasource.test3.url}")
    private String test3Url;

    @Value("${spring.datasource.test3.username}")
    private String test3Username;

    @Value("${spring.datasource.test3.password}")
    private String test3Password;

    @Bean(name="test3DataSource")
    public DataSource test3DataSource() throws Exception{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(test3Driver);
        dataSource.setJdbcUrl(test3Url);
        dataSource.setUsername(test3Username);
        dataSource.setPassword(test3Password);
        return dataSource;
    }
}
