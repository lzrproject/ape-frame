package com.paopao.tool.timeout;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * @Author 111
 * @Date 2022/8/10 20:58
 * @Description 注入过滤器（指定过滤接口）
 */
//@Configuration
public class ApiWebConfig {

    @Bean
    public FilterRegistrationBean<Filter> registrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiRequestFilter());
        registrationBean.addUrlPatterns("/test/request");
        registrationBean.setOrder(-1);
        return registrationBean;
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");
//    }
}
