package com.itan.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/10
 */
public class CASTest1 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println("是否更新成功：" + atomicInteger.compareAndSet(5, 10) + "，值为：" + atomicInteger.get());
        System.out.println("是否更新成功：" + atomicInteger.compareAndSet(5, 20) + "，值为：" + atomicInteger.get());
    }
}
