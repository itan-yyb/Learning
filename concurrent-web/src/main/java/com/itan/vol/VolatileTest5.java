package com.itan.vol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/27
 * 使用synchronized保证原子性
 * 使用原子类保证原子性
 */
public class VolatileTest5 {
    volatile int num = 0;

    //原子类
    AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        VolatileTest5 test4 = new VolatileTest5();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    test4.addNum();
                    test4.addAtomicInteger();
                }
            }, String.valueOf(i)).start();
        }
        //等待上面10个线程全部计算完成后，再用main线程取得最终结果值
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("int type, finally number value：" + test4.num);
        System.out.println("atomicInteger type, finally number value：" + test4.atomicInteger);
    }

    /**
     * 在addNum方法上加synchronized关键字，保证原子性
     */
    public synchronized void addNum(){
        num ++;
    }

    /**
     * 使用原子类，每次加1
     */
    public void addAtomicInteger() {
        //以原子方式将当前值加1
        atomicInteger.getAndIncrement();
    }
}
