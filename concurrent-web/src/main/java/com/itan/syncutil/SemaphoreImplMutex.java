package com.itan.syncutil;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/11
 * Semaphore实现互斥锁，类似synchronized效果
 */
@Slf4j
public class SemaphoreImplMutex {
    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        SemaphoreImplMutex semaphoreMutex = new SemaphoreImplMutex();
        for (int i = 1; i < 4; i++) {
            new Thread(semaphoreMutex::method, "线程 " + i).start();
        }
    }

    public void method() {
        //同时只会有一个线程执行此方法！
        try {
            semaphore.acquire();
            log.info(Thread.currentThread().getName() + " 正在执行！");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            log.info(Thread.currentThread().getName() + " 执行结束！");
        }
    }
}
/**
 * 23:05:07.154 [线程 1] INFO com.itan.syncutil.SemaphoreImplMutex - 线程 1 正在执行！
 * 23:05:08.180 [线程 1] INFO com.itan.syncutil.SemaphoreImplMutex - 线程 1 执行结束！
 * 23:05:08.186 [线程 3] INFO com.itan.syncutil.SemaphoreImplMutex - 线程 3 正在执行！
 * 23:05:09.200 [线程 3] INFO com.itan.syncutil.SemaphoreImplMutex - 线程 3 执行结束！
 * 23:05:09.200 [线程 2] INFO com.itan.syncutil.SemaphoreImplMutex - 线程 2 正在执行！
 * 23:05:10.201 [线程 2] INFO com.itan.syncutil.SemaphoreImplMutex - 线程 2 执行结束！
 */