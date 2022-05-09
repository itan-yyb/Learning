package com.itan.vol;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/27
 * 验证volatile不保证原子性
 */
public class VolatileTest4 {
    volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {
        VolatileTest4 test4 = new VolatileTest4();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    test4.addNum();
                }
            }, String.valueOf(i)).start();
        }
        //等待上面10个线程全部计算完成后，再用main线程取得最终结果值
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(test4.num);
    }

    public void addNum(){
        num ++;
    }
}













