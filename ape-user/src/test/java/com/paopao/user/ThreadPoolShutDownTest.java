package com.paopao.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author paoPao
 * @Date 2023/3/9
 * @Description: 优雅的线程池关闭
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadPoolShutDownTest {

    @Test
    public void testShutDown() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new TaskShutDownPool());
        }
        Thread.sleep(1000);
        /*
        shutdown()：关闭线程，不再执行新任务，已经启动的线程会等执行结束。
        shutdownNow()：立即关闭当前执行线程（通过Thread.interrupt中断），可以配合awaitTermination方法等待。
        isShutdown()：线程是否关闭，status为true。
        isTerminated()：线程是否关闭，当线程无任务执行时status为true，否则为false。
        awaitTermination()：检测线程是否关闭，根据time延迟检测，任务执行完则返回ture，否则为false
         */
        Thread.sleep(1000);
        log.info("testShutDown.status：{},before", executorService.isTerminated());
        executorService.shutdown();
        log.info("testShutDown.status：{},after", executorService.isTerminated());
        Thread.sleep(500);
        log.info("testShutDown.shutdown");
        Thread.sleep(5000);
        log.info("testShutDown.status：{},result", executorService.awaitTermination(5, TimeUnit.MINUTES));
        executorService.execute(new TaskShutDownPool());
    }

    class TaskShutDownPool implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                log.info("TaskShutDownPool.{}", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                log.info("TaskShutDownPool.interrupted：{}", e.getMessage(), e);
            }
        }
    }
}
