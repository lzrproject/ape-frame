package com.paopao.tool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: paoPao
 * @Date: 2023/3/11
 * @Description: 线程池工具类
 */
@Slf4j
public class ThreadPoolUtils {

    private ThreadPoolUtils() {

    }

    /**
     * 优雅关闭线程池
     * @param pool 线程池
     * @param shutDownTimeout 第一次shutdown时间
     * @param shutDownNowTimeOut 第二次shutdown时间
     * @param timeUnit 时间单位
     */
    public static void shutDownPool(ExecutorService pool, int shutDownTimeout, int shutDownNowTimeOut, TimeUnit timeUnit) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(shutDownTimeout,timeUnit)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(shutDownNowTimeOut,timeUnit)) {
                    log.error("ThreadPoolUtils.shutDownPool.error");
                }
            }
        }catch (InterruptedException e) {
            log.error("ThreadPoolUtils.shutDownPool.Interrupted.error:{}",e.getMessage(),e);
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
