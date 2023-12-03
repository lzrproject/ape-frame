package com.paopao.user.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2023/11/19
 */
public class Semaphore2 {

    static volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);
        Semaphore semaphore3 = new Semaphore(0);
        new Thread(() -> {
            try {
                while (a <= 100) {
                    semaphore1.acquire();
                    System.out.println(a);
                    a += 1;
                    semaphore2.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1").start();
        new Thread(() -> {
            try {
                while (a <= 100) {
                    semaphore2.acquire();
                    System.out.println(a);
                    a += 1;
                    semaphore3.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2").start();
        new Thread(() -> {
            try {
                while (a <= 100) {
                    semaphore3.acquire();
                    System.out.println(a);
                    a += 1;
                    semaphore1.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T3").start();
    }
}