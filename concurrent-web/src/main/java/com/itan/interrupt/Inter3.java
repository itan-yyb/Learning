package com.itan.interrupt;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/18
 * 通过Thread中断API停止线程
 */
public class Inter3 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "停止运行，中断标识位为：" + Thread.currentThread().isInterrupted());
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "正在运行，中断标识位为" + Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();

        new Thread(() -> {
            //打断t1线程
            t1.interrupt();
            System.out.println(Thread.currentThread().getName() + "将t1线程的中断标识位设置为：" + true);
        }, "t2").start();
    }
}
/**
 * 运行结果如下：
 * t1正在运行，中断标识位为false
 * t1正在运行，中断标识位为false
 * t1正在运行，中断标识位为false
 * t1正在运行，中断标识位为false
 * t2将t1线程的中断标识位设置为：true
 * t1停止运行，中断标识位为：true
 */