package com.itan.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/19
 */
public class LockConditionTest {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareResource.printAll(5);
            }
        }, "线程1").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareResource.printAll(10);
            }
        }, "线程2").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareResource.printAll(15);
            }
        }, "线程3").start();
    }
}
class ShareResource {
    private int number = 1;

    private Lock lock = new ReentrantLock();

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                c1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\tAA");
            }
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\tBB");
            }
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\tCC");
            }
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printAll(int num) {
        lock.lock();
        try {
            Condition awaitCondition = null;
            Condition signalCondition = null;
            int cycle = 1;
            String p = null;
            if (num == 5) {
                cycle = 1;
                p = "AAAA";
                awaitCondition = c1;
                signalCondition = c2;
            }
            if (num == 10) {
                cycle = 2;
                p = "BBBB";
                awaitCondition = c2;
                signalCondition = c3;
            }
            if (num == 15) {
                cycle = 3;
                p = "CCCC";
                awaitCondition = c3;
                signalCondition = c1;
            }
            while (number != cycle) {
                awaitCondition.await();
            }
            for (int i = 1; i <= num; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + p);
            }
            if (cycle == 3) {
                cycle = 0;
            }
            number = cycle + 1;
            signalCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
