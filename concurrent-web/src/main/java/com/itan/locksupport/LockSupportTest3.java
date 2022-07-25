package com.itan.locksupport;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/20
 * 正确使用LockSupport实现线程等待与唤醒
 */
@Slf4j
public class LockSupportTest3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info(Thread.currentThread().getName() + "开始执行");
            //等待
            LockSupport.park();
            log.info(Thread.currentThread().getName() + "线程被唤醒了");
        }, "t1");
        t1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            log.info(Thread.currentThread().getName() + "线程通知唤醒");
            //唤醒
            LockSupport.unpark(t1);
        }, "t2").start();
    }
}
/**
 * 运行结果如下：t1线程在运行2后被t2线程唤醒继续执行
 * 23:52:53.771 [t1] INFO com.itan.locksupport.LockSupportTest3 - t1开始执行
 * 23:52:55.767 [t2] INFO com.itan.locksupport.LockSupportTest3 - t2线程通知唤醒
 * 23:52:55.768 [t1] INFO com.itan.locksupport.LockSupportTest3 - t1线程被唤醒了
 */
