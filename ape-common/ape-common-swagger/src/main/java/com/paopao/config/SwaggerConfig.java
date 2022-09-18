package com.paopao.config;

import com.paopao.bean.SwaggerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author 111
 * @Date 2022/9/18 18:33
 * @Description swagger 配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerInfo swaggerInfo;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .contact(new Contact(swaggerInfo.getContactName(),
                        swaggerInfo.getContactUrl(),
                        swaggerInfo.getEmail()))
                .version(swaggerInfo.getVersion())
                .description(swaggerInfo.getDescription())
                .build();
    }
}
