package com.itan.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/24
 * 实现自旋锁，通过CAS操作完成自旋锁，A线程先持有锁5秒钟，
 * B线程发现当前有线程持有锁，自旋等待，直到A释放锁。
 * 运行结果：
 *      线程A进入lock
 *      线程B进入lock
 *      线程B正在自旋
 *      线程B正在自旋
 *      线程B正在自旋
 *      线程A退出，设置为null
 *      线程B正在自旋
 *      线程B退出，设置为null
 */
public class SpinLockTest {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 加锁
     */
    public void lock() {
        int i = 0;
        //获取当前线程
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "进入lock");
        //开始自旋，期望值是null，更新值是当前线程，如果是null，则更新为当前线程，否则自旋
        while (!atomicReference.compareAndSet(null, thread)) {
            i ++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(thread.getName() + "正在自旋");
            if (i == 2) {
                System.out.println("重试失败!");
                break;
            }
        }
    }

    /**
     * 解锁
     */
    public void unLock() {
        //获取当前线程
        Thread thread = Thread.currentThread();
        //用完之后，把atomicReference变成null
        boolean b = atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "退出，设置为null，是否成功 " + b);
    }

    public static void main(String[] args) {
        SpinLockTest test = new SpinLockTest();
        new Thread(() -> {
            //占有锁
            test.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //释放锁
            test.unLock();
        }, "线程A").start();

        //主线程暂停1秒，使得线程A先执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            //占有锁
            test.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 开始释放锁
            test.unLock();
        }, "线程B").start();
    }
}
