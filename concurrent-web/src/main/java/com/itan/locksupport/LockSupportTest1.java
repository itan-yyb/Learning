package com.itan.locksupport;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/20
 * 正确使用wait与notify实现线程等待与唤醒
 */
@Slf4j
public class LockSupportTest1 {
    public static void main(String[] args) {
        Object objectLock = new Object(); //同一把锁，类似资源类
        new Thread(() -> {
            log.info(Thread.currentThread().getName() + "开始执行");
            synchronized (objectLock) {
                try {
                    //等待
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info(Thread.currentThread().getName() + "线程被唤醒了");
        }, "t1").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (objectLock) {
                //唤醒
                objectLock.notify();
            }
        }, "t2").start();
    }
}
/**
 * 运行结果如下：t1线程在运行2后被t2线程唤醒继续执行
 * 23:49:21.669 [t1] INFO com.itan.locksupport.LockSupportTest1 - t1开始执行
 * 23:49:23.667 [t1] INFO com.itan.locksupport.LockSupportTest1 - t1线程被唤醒了
 */
