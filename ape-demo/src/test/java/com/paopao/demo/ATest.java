package com.paopao.demo;

import cn.hutool.core.lang.UUID;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

/**
 * Caffeine 原子性
 *
 * @Author paoPao
 * @Date 2023/12/11
 */
@SpringBootTest
public class ATest {

    @Test
    public void test1() throws InterruptedException {
        Cache<String, String> SECOND_3 = Caffeine.newBuilder().initialCapacity(16)
                .maximumSize(256).build();
        String key = "uuid_test";
        new Thread(() -> {
            String token = UUID.fastUUID().toString();
            String ob = SECOND_3.get(key, k -> token);
            if (token.equals(ob)) {
                System.out.println("111");
            }
        }).start();

        new Thread(() -> {
            String token = UUID.fastUUID().toString();
            String ob = SECOND_3.get(key, k -> token);
            if (token.equals(ob)) {
                System.out.println("222");
            }
        }).start();

        new Thread(() -> {
            String token = UUID.fastUUID().toString();
            String ob = SECOND_3.get(key, k -> token);
            if (token.equals(ob)) {
                System.out.println("333");
            }
        }).start();

        new Thread(() -> {
            String token = UUID.fastUUID().toString();
            String ob = SECOND_3.get(key, k -> token);
            if (token.equals(ob)) {
                System.out.println("444");
            }
        }).start();

        new Thread(() -> {
            String token = UUID.fastUUID().toString();
            String ob = SECOND_3.get(key, k -> token);
            if (token.equals(ob)) {
                System.out.println("555");
            }
        }).start();

        new Thread(() -> {
            String token = UUID.fastUUID().toString();
            String ob = SECOND_3.get(key, k -> token);
            if (token.equals(ob)) {
                System.out.println("666");
            }
        }).start();

        Thread.sleep(2000);
    }
}
