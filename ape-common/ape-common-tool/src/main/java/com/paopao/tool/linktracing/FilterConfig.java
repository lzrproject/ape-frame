package com.paopao.tool.linktracing;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @Author: paoPao
 * @Date: 2023/3/5
 * @Description: 过滤器注入配置
 */
@Configuration
public class FilterConfig {

    /**
     * 链路追踪
     */
    @Bean
    public FilterRegistrationBean registrationFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceIdFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("traceIdFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
