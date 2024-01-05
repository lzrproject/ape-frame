package com.paopao.demo;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2024/1/1
 */
@Slf4j
public class ExecutorTest {

    private ThreadPoolExecutor thread = new ThreadPoolExecutor(4, 8, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.DiscardPolicy());

    private ThreadLocal<List<Integer>> threadLocal = ThreadLocal.withInitial(ArrayList::new);

    @Test
    public void test() throws InterruptedException {
        int count = 100000;
        int batchNum = 3000;
        int i = 1;
        List<Integer> instanceIds = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(count / batchNum);
        while (i <= count) {
            instanceIds.add(i);
            if (instanceIds.size() == batchNum) {
                List<Integer> ids = new ArrayList<>(instanceIds);
                instanceIds.clear();
                CompletableFuture.runAsync(() -> {
                    for (int j = 0; j < batchNum; j += 500) {
                        saveData(ids.subList(j, Math.min(j + 500, batchNum)));
                    }
//                    threadLocal.set(ids);
//                    saveData(threadLocal.get());
//                    threadLocal.remove();
                    latch.countDown();
                }, thread);
            }
            i++;
        }
        latch.await();
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    @Test
    public void test11() {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

                System.out.println(snowflake.nextId());
            }, thread);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    @Test
    public void transactionalTest() {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                return transactionTemplate.execute(transactionStatus -> {
                    String fileName = "";
                    try {
                        System.out.println(Thread.currentThread().getName());
                        if (finalI == 8) {
                            throw new Exception("异常");
                        }
                    } catch (Exception e) {
                        log.error("JhemrCda38Service.saveDataToEntity.error：{}", e.getMessage(), e);
                        transactionStatus.setRollbackOnly();
                        // 写入文件
                        fileName = this.writeFile(1 + "_" + "CDA38");
                    }
                    return fileName;
                });
            }, thread);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private String writeFile(String fileName) {
        String nowDate = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        fileName = fileName + "_" + nowDate;
        log.info("SplitUtil.writeFile.fileName：{}，END", fileName);
        return fileName;
    }

    private void saveData(List<Integer> instanceIds) {
        System.out.println(instanceIds);
    }
}
