package com.itan.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/14
 * ABA问题产生
 */
public class ABAQuestion {
    static AtomicReference<String> reference = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            //先将A改为B，再又改回A
            System.out.println("线程 " + Thread.currentThread().getName() + " change A -> B " + reference.compareAndSet(reference.get(),"B"));
            System.out.println("线程 " + Thread.currentThread().getName() + " change B -> A " + reference.compareAndSet(reference.get(), "A"));
        },"t1").start();

        //暂停1s，保证t1线程能够完成一次ABA操作
        Thread.sleep(1000);
        new Thread(() -> {
            System.out.println("线程 " + Thread.currentThread().getName() + " change A -> C " + reference.compareAndSet(reference.get(), "C"));
        },"t2").start();
    }
}
/**
 * 运行结果如下：
 * 线程 t1 change A -> B true
 * 线程 t1 change B -> A true
 * 线程 t2 change A -> C true
 */