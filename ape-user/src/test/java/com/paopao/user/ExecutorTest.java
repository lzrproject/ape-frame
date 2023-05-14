package com.paopao.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: paoPao
 * @Date: 2023/3/18
 * @Description:
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ExecutorTest {

    //    @Resource(name = "mailThreadPool")
//    private ThreadPoolExecutor mailThreadPool;
    private final static ThreadPoolExecutor cdaExecutor = new ThreadPoolExecutor(100, 200,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100), new ThreadPoolExecutor.DiscardPolicy());

//    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void requestTime() {
        exec(100);
    }

    public void exec(int taskNum) {
        long start = System.currentTimeMillis();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < taskNum; i++) {
            User user = new User();
            user.setUsername("admin" + i);
            cdaExecutor.submit(new Task(user));
        }
        try {
            Thread.sleep(1000);
            cdaExecutor.shutdown();
            cdaExecutor.awaitTermination(1, TimeUnit.DAYS);
            long end = System.currentTimeMillis();
            log.info("{},costTime:{}", Thread.currentThread().getName(), end - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Task implements Runnable {
        private User user;

        public Task(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                log.info("{},Task.username:{}", Thread.currentThread().getName(), user.getUsername());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

@Data
class User {
    private String username;

    public User(String username) {
        this.username = username;
    }
}
