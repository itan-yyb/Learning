package com.itan.lock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/23
 * 验证synchronized可重入：同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * 结果：
 * t1调用method1方法
 * t1调用method2方法
 * t2调用method1方法
 * t2调用method2方法
 */
public class SyncLockTest {
    public static void main(String[] args) {
        SyncLockTest test = new SyncLockTest();
        //两个线程操作
        new Thread(() ->{
            test.method1();
        }, "t1").start();

        new Thread(() ->{
            test.method1();
        }, "t2").start();
    }

    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + "调用method1方法");
        //在同步方法中，调用另一个同步方法
        method2();
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + "调用method2方法");
    }
}
