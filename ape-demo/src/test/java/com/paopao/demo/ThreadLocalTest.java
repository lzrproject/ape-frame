package com.paopao.demo;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/31
 */
public class ThreadLocalTest {

    @Test
    public void test1() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4,
                10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        InheritableThreadLocal<String> username = new InheritableThreadLocal<>();
        for (int i = 0; i < 10; i++) {
            username.set("消息1: " + i);
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " " + username.get());
                    username.remove();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executor);
        }
        System.in.read();
    }
}
