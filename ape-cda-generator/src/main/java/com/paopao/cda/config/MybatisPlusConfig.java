package com.paopao.cda.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 监听某些表 增加后缀
 * https://blog.csdn.net/qq_44824400/article/details/125604157
 * https://www.jb51.net/program/292171m79.htm
 *
 * @Author paoPao
 * @Date 2024/1/24
 */
@Configuration
public class MybatisPlusConfig {


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicInterceptor = new DynamicTableNameInnerInterceptor();
        Map<String, TableNameHandler> map = new HashMap<>();
        map.put("cda_dynamic_table", new DynamicTableHandle());
        dynamicInterceptor.setTableNameHandlerMap(map);
        interceptor.addInnerInterceptor(dynamicInterceptor);
        return interceptor;
    }
}
