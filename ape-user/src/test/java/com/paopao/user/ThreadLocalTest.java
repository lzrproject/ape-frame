package com.paopao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: paoPao
 * @Date: 2023/3/5
 * @Description: ThreadLocal 与 InheritableThreadLocal的区别
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ThreadLocalTest {
    public static final ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
    public static final ThreadLocal<String> threadLocal2 = new InheritableThreadLocal<>();

    @Test
    public void test1() throws InterruptedException {
        threadLocal1.set("threadLocalTest1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+": "+threadLocal1.get());
                threadLocal1.set("threadLocalTest1.1");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+": "+threadLocal1.get());
                threadLocal1.remove();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println("main线程："+threadLocal1.get());
    }

    /**
     *  InheritableThreadLocal 只有父线程set的值对子线程可见，子线程之间是不可见的。
     */
    @Test
    public void test2() throws InterruptedException {
        threadLocal2.set("threadLocalTest1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+": "+threadLocal2.get());
                threadLocal2.set("threadLocalTest1.1");
                System.out.println(Thread.currentThread().getName()+": "+threadLocal2.get());
            }
        }).start();
        threadLocal2.set("threadLocalTest2");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+": "+threadLocal2.get());
                threadLocal1.remove();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println("main线程："+threadLocal2.get());
    }
}
