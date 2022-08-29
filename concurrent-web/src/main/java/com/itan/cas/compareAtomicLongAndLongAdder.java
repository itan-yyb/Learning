package com.itan.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/14
 * 性能对比
 */
public class compareAtomicLongAndLongAdder {
    /** 累加次数 */
    private static final int ADD_COUNT = 10000;
    /** 线程数 */
    private static final int THREAD_COUNT = 50;

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        //设置访问私有变量
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);
        System.out.println(unsafe);
        long startTime, endTime;
        //资源类
        CompareResource compareResource = new CompareResource();
        //计数器
        CountDownLatch c1 = new CountDownLatch(THREAD_COUNT);
        CountDownLatch c2 = new CountDownLatch(THREAD_COUNT);
        CountDownLatch c3 = new CountDownLatch(THREAD_COUNT);
        CountDownLatch c4 = new CountDownLatch(THREAD_COUNT);
        //开始比较
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * ADD_COUNT; j++) {
                        compareResource.addSynchronizedNum();
                    }
                } finally {
                    c1.countDown();
                }
            }, String.valueOf(i)).start();
        }
        c1.await();
        endTime = System.currentTimeMillis();
        System.out.println("使用Synchronized方式计算耗时：" + (endTime - startTime) + " 毫秒" + " 最后结果: " + compareResource.number);

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * ADD_COUNT; j++) {
                        compareResource.addAtomicLongNum();
                    }
                } finally {
                    c2.countDown();
                }
            }, String.valueOf(i)).start();
        }
        c2.await();
        endTime = System.currentTimeMillis();
        System.out.println("使用AtomicLong方式计算耗时：" + (endTime - startTime) + " 毫秒" + " 最后结果: " + compareResource.atomicLong.get());

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * ADD_COUNT; j++) {
                        compareResource.addLongAdderNum();
                    }
                } finally {
                    c3.countDown();
                }
            }, String.valueOf(i)).start();
        }
        c3.await();
        endTime = System.currentTimeMillis();
        System.out.println("使用LongAdder方式计算耗时：" + (endTime - startTime) + " 毫秒" + " 最后结果: " + compareResource.longAdder.sum());

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * ADD_COUNT; j++) {
                        compareResource.addLongAccumulatorNum();
                    }
                } finally {
                    c4.countDown();
                }
            }, String.valueOf(i)).start();
        }
        c4.await();
        endTime = System.currentTimeMillis();
        System.out.println("使用LongAccumulator方式计算耗时：" + (endTime - startTime) + " 毫秒" + " 最后结果: " + compareResource.longAccumulator.get());
    }
}

/**
 * 资源类
 */
class CompareResource {
    public int number = 0;

    /**
     * 使用synchronized进行累加
     */
    public synchronized void addSynchronizedNum() {
        number ++;
    }

    /**
     * 使用原子类进行累加
     */
    AtomicLong atomicLong = new AtomicLong(0);
    public void addAtomicLongNum() {
        atomicLong.getAndIncrement();
    }

    /**
     * 使用LongAdder进行累加
     */
    LongAdder longAdder = new LongAdder();
    public void addLongAdderNum() {
        longAdder.increment();
    }

    /**
     * 使用LongAccumulator进行累加，是long类型的聚合器，
     * 需要传入一个long类型的二元操作，可以用来计算各种聚合操作，包括加乘等
     */
    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
    public void addLongAccumulatorNum() {
        longAccumulator.accumulate(1);
    }
}
/**
 * 运行结果如下：可以发现LongAdder与LongAccumulator计算耗时最少，性能最高
 * 使用Synchronized方式计算耗时：3522 毫秒 最后结果: 50000000
 * 使用AtomicLong方式计算耗时：1696 毫秒 最后结果: 50000000
 * 使用LongAdder方式计算耗时：357 毫秒 最后结果: 50000000
 * 使用LongAccumulator方式计算耗时：358 毫秒 最后结果: 50000000
 */