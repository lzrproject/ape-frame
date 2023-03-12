package com.paopao.tool;

import org.slf4j.Logger;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author paoPao
 * @Date 2023/2/28
 * @Description: 任务超时日志
 */
public class CompletableFutureUtils {

    public static <T> T getResult(Future<T> future, long timeOut, TimeUnit timeUnit,
                                  T defaultValue, Logger logger) {
        try {
            return future.get(timeOut, timeUnit);
        } catch (Exception e) {
            logger.error("CompletableFutureUtils.getResult.error:{}", e.getMessage(), e);
            ;
            return defaultValue;
        }
    }
}
