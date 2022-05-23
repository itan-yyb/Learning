package com.itan.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/14
 * 解决ABA问题
 */
public class SolveABAQuestion {
    static AtomicStampedReference<String> reference1 = new AtomicStampedReference<>("A",1);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            //获取版本号
            int stamp = reference1.getStamp();
            System.out.println("线程 " + Thread.currentThread().getName() + " 初始版本号 " + stamp);
            //暂停2s，保证t2线程能够取得初始版本号
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //先将A改为B，再又改回A，每次修改版本号+1
            boolean oneFlag = reference1.compareAndSet("A", "B", reference1.getStamp(), reference1.getStamp() + 1);
            System.out.println("线程 " + Thread.currentThread().getName() + " change A -> B " + oneFlag + " 最新值为 " + reference1.getReference() + " 版本号为 " + reference1.getStamp());

            boolean twoFlag = reference1.compareAndSet("B", "A", reference1.getStamp(), reference1.getStamp() + 1);
            System.out.println("线程 " + Thread.currentThread().getName() + " change B -> A " + twoFlag + " 最新值为 " + reference1.getReference() + " 版本号为 " + reference1.getStamp());
        }, "t1").start();

        new Thread(() -> {
            //获取版本号
            int stamp = reference1.getStamp();
            System.out.println("线程 " + Thread.currentThread().getName() + " 初始版本号 " + stamp);
            //暂停2s，保证t1线程能够完成一次ABA操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag = reference1.compareAndSet("A", "C", stamp, stamp + 1);
            System.out.println("线程 " + Thread.currentThread().getName() + " change A -> C " + flag + " 最新值为 " + reference1.getReference() + " 版本号为 " + reference1.getStamp());
        }, "t2").start();
    }
}
/**
 * 运行结果如下：
 * 线程 t1 初始版本号 1
 * 线程 t2 初始版本号 1
 * 线程 t1 change A -> B true 最新值为 B 版本号为 2
 * 线程 t1 change B -> A true 最新值为 A 版本号为 3
 * 线程 t2 change A -> C false 最新值为 A 版本号为 3
 */