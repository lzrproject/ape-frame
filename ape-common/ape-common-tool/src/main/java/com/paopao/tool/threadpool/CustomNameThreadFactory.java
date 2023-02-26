package com.paopao.tool.threadpool;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: paoPao
 * @Date: 2023/2/26
 * @Description:
 */
public class CustomNameThreadFactory implements ThreadFactory {

    private final AtomicInteger poolNumber = new AtomicInteger(1);

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final ThreadGroup group;

    private final String namePrefix;

    public CustomNameThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        if (StringUtils.isBlank(name)) {
            name = "pool";
        }
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        // stackSize 每个线程的栈空间
        Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        // 判断该线程是否为守护线程
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        // getPriority() 线程优先级  优先级在1到10的范围内。线程的默认优先级为5。
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
