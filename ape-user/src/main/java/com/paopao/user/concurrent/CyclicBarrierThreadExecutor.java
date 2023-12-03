package com.paopao.user.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2023/11/19
 */
public class CyclicBarrierThreadExecutor {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(new MyThread(barrier), "T1").start();
        barrier.await();
        new Thread(new MyThread(barrier), "T2").start();
        barrier.await();
        new Thread(new MyThread(barrier), "T3").start();
        barrier.await();
    }
}

class MyThread implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public MyThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
//            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "is running");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
