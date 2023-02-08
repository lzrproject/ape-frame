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
public class Test2DataSourceBean {

    @Value("${spring.datasource.test2.driver-class-name}")
    private String test2Driver;

    @Value("${spring.datasource.test2.url}")
    private String test2Url;

    @Value("${spring.datasource.test2.username}")
    private String test2Username;

    @Value("${spring.datasource.test2.password}")
    private String test2Password;

    @Bean(name="test2DataSource")
    public DataSource test2DataSource() throws Exception{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(test2Driver);
        dataSource.setJdbcUrl(test2Url);
        dataSource.setUsername(test2Username);
        dataSource.setPassword(test2Password);
        return dataSource;
    }
}
