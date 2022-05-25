package com.itan.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/23
 * 验证ReentrantLock可重入：同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * 结果：与加synchronized关键字的方法是一致的，都是在外层的方法获取锁之后，线程能够进入内层
 * t1调用method1方法
 * t1调用method2方法
 * t2调用method1方法
 * t2调用method2方法
 */
public class ReenLockTest {
    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReenLockTest test = new ReenLockTest();
        //两个线程操作
        new Thread(() ->{
            //进入method1方法时，就加了锁，再在方法中调用另一个加了锁的方法
            test.method1();
        }, "t1").start();

        new Thread(() ->{
            //进入method1方法时，就加了锁，再在方法中调用另一个加了锁的方法
            test.method1();
        }, "t2").start();
    }

    public void method1() {
        //加锁
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "调用method1方法");
            //调用另一个加了锁的方法
            method2();
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    public void method2() {
        //加锁
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "调用method2方法");
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
