package com.itan.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/16
 * 重量级锁，多个线程抢锁
 */
public class HeavyWeightLock {
    public static void main(String[] args) {
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj){
                // 重量级锁
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (obj){
                // 重量级锁
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
        }, "t2").start();
        new Thread(() -> {
            synchronized (obj){
                // 重量级锁
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
        }, "t3").start();
    }
}
