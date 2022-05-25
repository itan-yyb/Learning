package com.itan.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/24
 * 读写锁问题解决
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        CacheMap cache = new CacheMap();
        //5个线程写
        for (int i = 1; i <= 5; i++) {
            //lambda表达式内部必须是final
            final int threadName = i;
            new Thread(() -> {
                cache.put(threadName + "", UUID.randomUUID().toString().substring(0, 6));
            }, "写线程 " + threadName).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //5个线程读
        for (int i = 1; i <= 5; i++) {
            final int threadName = i;
            new Thread(() -> {
                cache.get(threadName + "");
            }, "读线程 " + threadName).start();
        }
    }
}

class CacheMap {
    /**
     * volatile：保证内存可见性
     */
    private volatile Map<String, Object> map = new HashMap<>();

    /**
     * 创建一个读写锁，它是一个读写融为一体的锁，在使用的时候，需要转换
     */
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 写操作
     * 满足：原子 + 独占，整个过程必须是一个完整的统一体，中间不允许被打断，被分割
     * @param k
     * @param v
     */
    public void put(String k, Object v) {
        //创建写锁
        readWriteLock.writeLock().lock();
        try {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + " 正在写入 " + k);
            //模拟网络拥堵，延迟0.3秒
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(k, v);
            System.out.println(thread.getName() + " 写入成功 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //写锁释放
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 读操作
     * @param k
     */
    public void get(String k) {
        //创建读锁
        readWriteLock.readLock().lock();
        try {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + " 正在读取 " + k);
            TimeUnit.MILLISECONDS.sleep(300);
            Object v = map.get(k);
            System.out.println(thread.getName() + " 读取成功 " + v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            readWriteLock.readLock().unlock();
        }
    }
}