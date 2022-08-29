package com.itan.cas;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/14
 */
public class AtomicMarkableReferenceTest {
    static AtomicMarkableReference<String> reference1 = new AtomicMarkableReference<>("A",false);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            //获取标记
            boolean marked = reference1.isMarked();
            System.out.println("线程 " + Thread.currentThread().getName() + " 初始标记 " + marked);
            //暂停2s，保证t2线程能够取得初始版本号
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag = reference1.compareAndSet("A", "B", marked, !marked);
            System.out.println("线程 " + Thread.currentThread().getName() + " change A -> B " + flag + " 最新值为 " + reference1.getReference() + " 标记为 " + reference1.isMarked());
        }, "t1").start();

        new Thread(() -> {
            //获取标记
            boolean marked = reference1.isMarked();
            System.out.println("线程 " + Thread.currentThread().getName() + " 初始标记 " + marked);
            //暂停2s，保证t1线程能够完成一次ABA操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag = reference1.compareAndSet("A", "C", marked, !marked);
            System.out.println("线程 " + Thread.currentThread().getName() + " change A -> C " + flag + " 最新值为 " + reference1.getReference() + " 版本号为 " + reference1.isMarked());
        }, "t2").start();
    }
}
/**
 * 运行结果如下：
 * 线程 t1 初始标记 false
 * 线程 t2 初始标记 false
 * 线程 t1 change A -> B true 最新值为 B 标记为 true
 * 线程 t2 change A -> C false 最新值为 B 版本号为 true
 */