package com.paopao.share.controller;

import com.paopao.share.ShareApplication;
import com.paopao.share.pojo.User;
import com.paopao.share.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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

    @Value("${student.lastNames}")
    private String[] lastNames;
    @Value("${student.firstNames}")
    private String[] firstNames;

    /**
     * cpu密集型和io密集型
     */
    private final ExecutorService executorService = new ThreadPoolExecutor(10, 100, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));

    private static final int THREAD_NUMS = 9;

    private final Executor pool = Executors.newFixedThreadPool(THREAD_NUMS);

    private long isSelect = 2;
    @Test
    public void test() {

//        try{
//            String s = lastNames[randomInt(14)] + firstNames[randomInt(31)];
//            String str3 = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//            System.out.println(str3);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void doInsert() {
        User user = new User();
        String s = lastNames[randomInt(14)] + firstNames[randomInt(31)];
        user.setUsername(s);
        user.setCreateTime(new Date());
        userService.save(user);
    }

    @Test
    public void doBatchInsert(){

        final int INSERT_NUM = 50000;
        StopWatch stopWatch = new StopWatch();
        ArrayList<CompletableFuture<Void>> list = new ArrayList<>();
        stopWatch.start();
        for (int i = 1; i <= 30; i++) {
            final int a = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                ArrayList<User> users = new ArrayList<>();
//                int tmp = (a - 1) * INSERT_NUM;
                for (int j = 1; j <= INSERT_NUM; j++) {
                    User user = new User();
                    String s = lastNames[randomInt(14)] + firstNames[randomInt(31)];
                    String username = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//                    user.setUsername("usersss" + (tmp + j));
//                    user.setPassword("aaasss");
                    user.setUsername(username);
                    user.setCreateTime(new Date());
                    users.add(user);
                }
                userService.saveBatch(users, 1000);
            }, pool);
            list.add(future);
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println("耗时"+stopWatch.getTotalTimeSeconds()+"ms");
    }

    /**
     * 获取随机数，取值范围为[0,(n-1)]
     * @param n
     * @return
     */
    private int randomInt(int n){
        Random random = new Random();
        return random.nextInt(n);
    }
}
