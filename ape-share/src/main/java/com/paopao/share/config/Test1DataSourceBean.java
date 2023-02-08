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
public class Test1DataSourceBean {
    @Value("${spring.datasource.test1.driver-class-name}")
    private String test1Driver;

    @Value("${spring.datasource.test1.url}")
    private String test1Url;

    @Value("${spring.datasource.test1.username}")
    private String test1Username;

    @Value("${spring.datasource.test1.password}")
    private String test1Password;

    @Bean(name="test1DataSource")
    public DataSource test1DataSource() throws Exception{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(test1Driver);
        dataSource.setJdbcUrl(test1Url);
        dataSource.setUsername(test1Username);
        dataSource.setPassword(test1Password);
        return dataSource;
    }
}
