package com.paopao.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
### xxl-job admin address list, such as "http://address" or "http://address01,http://address02"
        xxl:
        job:
        admin:
        addresses: http://127.0.0.1:9000/xxl-job-admin
        accessToken: default_token
        executor:
        appname: ape-frame
        address:
        ip: 127.0.0.1
        port: 9999
        #      logpath:  /data/applogs/xxl-job/jobhandle
        logpath:  D:/idea/IdeaProjects/Java/jingdianjichi/log/xxl-job/jobhandle
        logretentiondays: 30
/**
 * @Author 111
 * @Date 2022/8/10 16:36
 * @Description
 *
 */
@SpringBootApplication(scanBasePackages = {"com.paopao.user","com.paopao"})
@MapperScan(basePackages = "com.paopao.user.mapper")
@EnableCaching
public class UserApplication {

    public static void main(String[] args) {
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(UserApplication.class);
    }
}
