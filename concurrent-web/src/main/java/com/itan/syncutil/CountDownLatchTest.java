package com.itan.syncutil;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/26
 * 5个人同时约定去旅游，等人到齐之后，乘车出发
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 1; i <= 5; i++) {
            final int name = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "到达目的地，等待出发");
                //计数减1
                countDownLatch.countDown();
            }, "兄弟" + name).start();
        }
        //等待所有线程执行完成。主线程才继续向下执行
        countDownLatch.await();
        System.out.println("乘车出发");
    }
}
