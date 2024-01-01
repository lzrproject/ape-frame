package com.paopao.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2024/1/1
 */
public class ExecutorTest {

    private ThreadPoolExecutor thread = new ThreadPoolExecutor(4, 8, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.DiscardPolicy());

    private ThreadLocal<List<Integer>> threadLocal = ThreadLocal.withInitial(ArrayList::new);

    @Test
    public void test() throws InterruptedException {
        int count = 10000;
        int batchNum = 100;
        int i = 1;
        List<Integer> instanceIds = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(count / batchNum);
        while (i <= count) {
            instanceIds.add(i);
            if (instanceIds.size() == batchNum) {
                List<Integer> ids = new ArrayList<>(instanceIds);
                instanceIds.clear();
                CompletableFuture.runAsync(() -> {
                    threadLocal.set(ids);
                    saveData(threadLocal.get());
                    threadLocal.remove();
                    latch.countDown();
                }, thread);
            }
            i++;
        }
        latch.await();
    }

    private void saveData(List<Integer> instanceIds) {
        System.out.println(threadLocal.get());
    }

}
