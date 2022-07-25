package com.itan.locksupport;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/20
 * 正确使用Condition实现线程等待与唤醒
 */
@Slf4j
public class LockSupportTest2 {
    public static void main(String[] args) {
        //创建Lock对象
        Lock lock = new ReentrantLock();
        //创建Condition
        Condition condition = lock.newCondition();
        new Thread(() -> {
            //加锁
            lock.lock();
            try {
                log.info(Thread.currentThread().getName() + "开始执行");
                //等待
                condition.await();
                log.info(Thread.currentThread().getName() + "线程被唤醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlock();
            }
        }, "t1").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            //加锁
            lock.lock();
            try {
                //唤醒
                condition.signal();
                log.info(Thread.currentThread().getName() + "线程通知唤醒");
            } finally {
                //释放锁
                lock.unlock();
            }
        }, "t2").start();
    }
}
/**
 * 运行结果如下：t1线程在运行2后被t2线程唤醒继续执行
 * 00:15:13.357 [t1] INFO com.itan.locksupport.LockSupportTest2 - t1开始执行
 * 00:15:15.364 [t2] INFO com.itan.locksupport.LockSupportTest2 - t2线程通知唤醒
 * 00:15:15.364 [t1] INFO com.itan.locksupport.LockSupportTest2 - t1线程被唤醒了
 */
