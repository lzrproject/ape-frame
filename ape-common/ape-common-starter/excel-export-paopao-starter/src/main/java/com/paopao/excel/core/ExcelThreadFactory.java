package com.paopao.excel.core;

import com.paopao.excel.utils.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Excel导出 自定义工厂
 *
 * @Author paoPao
 * @Date 2023/10/27
 */
public class ExcelThreadFactory implements ThreadFactory {

    private final AtomicInteger poolNumber = new AtomicInteger(1);

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final ThreadGroup threadGroup;

    public final String namePrefix;

    public ExcelThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (StringUtils.isEmpty(name)) {
            name = "pool";
        }
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
