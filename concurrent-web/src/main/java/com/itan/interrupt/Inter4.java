package com.itan.interrupt;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/18
 * 线程处于sleep、wait、join等状态被打断，抛出InterruptedException，并清除中断标识
 */
public class Inter4 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "出现异常后，中断标识位为：" + Thread.currentThread().isInterrupted());
                    /**
                     * 当出现InterruptedException异常的时候，中断标识被清除（false），导出程序无法停止，进入死循环
                     * Thread.currentThread().interrupt();再次将中断标识设置为true
                     */
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "停止运行，中断标识位为：" + Thread.currentThread().isInterrupted());
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "正在运行，中断标识位为" + Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);

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
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.itan.interrupt.Inter4.lambda$main$0(Inter4.java:14)
 * 	at java.lang.Thread.run(Thread.java:748)
 */