package ${config.pathInfo.packageName}.config;

import ${package.Controller}.${table.controllerName};
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};;
import ${package.ServiceImpl}.${table.serviceImplName};
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@MapperScan(basePackages = {"${config.pathInfo.packageName}.mapper"})
//@ConditionalOnClass(value = {PaginationInterceptor.class})
public class MyBatisPlusConfig {

    @Resource
    private ${table.mapperName} ${table.mapperName?uncap_first};

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @Bean
    public ${table.serviceName} ${table.serviceName?uncap_first}(){
        return new ${table.serviceImplName}(${table.mapperName?uncap_first});
    }

    @Bean
    public ${table.controllerName} ${table.controllerName?uncap_first}(){
        return new ${table.controllerName}(${table.serviceName?uncap_first});
    }
}


