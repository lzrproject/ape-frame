package com.paopao.user.controller;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: paoPao
 * @Date: 2023/3/14
 * @Description:
 */
@RestController
@RequestMapping("threadPool")
@Slf4j
public class ThreadPoolController {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 50, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(50), new ThreadPoolExecutor.DiscardPolicy());

    @GetMapping("exec")
    public void execService() {
        for (int i = 0; i < 5; i++) {
            Future<?> future = executor.submit(new Runnable() {
                @Override
                public void run() {
                    test();
                }
            });
            
        }
    }

    public void test() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("{} test.costTime：{}", Thread.currentThread().getName(), end - start);
    }
}
