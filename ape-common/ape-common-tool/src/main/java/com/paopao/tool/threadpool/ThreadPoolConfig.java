package com.paopao.tool.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: paoPao
 * @Date: 2023/2/26
 * @Description: 自定义线程池
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("mailThreadPool")
    public ThreadPoolExecutor getMailThreadPool() {
        CustomNameThreadFactory mailFactory = new CustomNameThreadFactory("mail");
        return new ThreadPoolExecutor(20, 50, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50), mailFactory,
                new ThreadPoolExecutor.DiscardPolicy());
    }

}
