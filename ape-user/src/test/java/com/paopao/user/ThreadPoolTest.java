package com.paopao.user;

import com.paopao.tool.CompletableFutureUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: paoPao
 * @Date: 2023/2/26
 * @Description: 线程池工具
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadPoolTest {

    @Resource(name = "mailThreadPool")
    private ThreadPoolExecutor mailThreadPool;

    @Test
    public void threadPoolTest() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            mailThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    test();
                    countDownLatch.countDown();
//                    log.info("当前时间：{}", System.currentTimeMillis());
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    @Test
    public void testFuture() {
        List<FutureTask<String>> futureTaskList = new ArrayList<>();
        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            return "经典";
        });
        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            return "经典";
        });
        futureTaskList.add(futureTask1);
        futureTaskList.add(futureTask2);
        mailThreadPool.submit(futureTask1);
        mailThreadPool.submit(futureTask2);

        futureTaskList.forEach(v -> {
            String result = CompletableFutureUtils.getResult(v, 1, TimeUnit.SECONDS, "经典鸡翅", log);
            log.info("testFuture:{}", result);
        });
    }
}
