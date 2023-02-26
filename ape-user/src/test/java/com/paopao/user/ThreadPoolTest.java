package com.paopao.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: paoPao
 * @Date: 2023/2/26
 * @Description:
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadPoolTest {

    @Resource(name = "mailThreadPool")
    private ThreadPoolExecutor mailThreadPool;

    @Test
    public void threadPoolTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mailThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        log.info("当前时间：{}", System.currentTimeMillis());
                    }
                });
            }
        }
    }
}
