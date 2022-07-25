package com.itan.interrupt;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/18
 * 通过volatile中断停止线程
 */
public class Inter1 {
    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + "停止运行，flag为：" + flag);
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "正在运行，flag为：" + flag);
            }
        }, "t1").start();

        Thread.sleep(1000);

        new Thread(() -> {
            flag = true;
            System.out.println(Thread.currentThread().getName() + "将flag修改为：" + true);
        }, "t2").start();
    }
}
/**
 * 运行结果如下：
 * t1正在运行，flag为：false
 * t1正在运行，flag为：false
 * t1正在运行，flag为：false
 * t1正在运行，flag为：false
 * t2将flag修改为：true
 * t1停止运行，flag为：true
 */