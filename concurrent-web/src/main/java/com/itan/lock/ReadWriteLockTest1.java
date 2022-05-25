package com.itan.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/24
 * 读写锁问题分析
 */
public class ReadWriteLockTest1 {
    public static void main(String[] args) {
        CacheMap1 cache = new CacheMap1();
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

class CacheMap1 {
    private volatile Map<String, Object> map = new HashMap<>();

    /**
     * 写操作
     * 满足：原子 + 独占，整个过程必须是一个完整的统一体，中间不允许被打断，被分割
     * @param k
     * @param v
     */
    public void put(String k, Object v) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " 正在写入 " + k);
        //模拟网络拥堵，延迟0.3秒
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(k, v);
        System.out.println(thread.getName() + " 写入成功 ");
    }

    /**
     * 读操作
     * @param k
     */
    public void get(String k) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " 正在读取 " + k);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object v = map.get(k);
        System.out.println(thread.getName() + " 读取成功 " + v);
    }
}