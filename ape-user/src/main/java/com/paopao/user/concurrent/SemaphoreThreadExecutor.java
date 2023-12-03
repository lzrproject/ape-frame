package com.paopao.user.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2023/11/19
 */
public class SemaphoreThreadExecutor {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        new Thread(new MyThreadSem(semaphore), "T1").start();
        semaphore.acquire();
        new Thread(new MyThreadSem(semaphore), "T2").start();
        semaphore.acquire();
        new Thread(new MyThreadSem(semaphore), "T3").start();
    }
}

class MyThreadSem implements Runnable {

    private Semaphore semaphore;

    public MyThreadSem(Semaphore semaphor) {
        this.semaphore = semaphor;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " is running");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
