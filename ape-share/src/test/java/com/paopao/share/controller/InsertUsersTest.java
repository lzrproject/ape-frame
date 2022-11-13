package com.paopao.share.controller;

import com.paopao.share.ShareApplication;
import com.paopao.share.pojo.User;
import com.paopao.share.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @Author 111
 * @Date 2022/8/30 22:07
 * @Description
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ShareApplication.class})
//@WebAppConfiguration
//@SpringBootConfiguration
public class InsertUsersTest {
    @Autowired
    private UserService userService;

    /**
     * cpu密集型和io密集型
     */
    private final ExecutorService executorService = new ThreadPoolExecutor(30, 100, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));

    @Test
    public void doInsertUser(){

        final int INSERT_NUM = 50000;
        StopWatch stopWatch = new StopWatch();
        ArrayList<CompletableFuture<Void>> list = new ArrayList<>();
        stopWatch.start();
        for (int i = 1; i <= 30; i++) {
            final int a = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                ArrayList<User> users = new ArrayList<>();
                int tmp = (a - 1) * INSERT_NUM;
                for (int j = 1; j <= INSERT_NUM; j++) {
                    User user = new User();
                    user.setUsername("usersss" + (tmp + j));
                    user.setPassword("aaasss");
                    users.add(user);
                }
                userService.saveBatch(users, 20000);
            }, executorService);
            list.add(future);
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println("耗时"+stopWatch.getTotalTimeSeconds()+"ms");
    }
}
