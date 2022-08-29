package com.itan.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/16
 * 验证在偏向锁状态，遇到一致性哈希码计算的请求时
 * 立马撤销偏向锁，膨胀为重量级锁
 */
public class LockTest1 {
    public static void main(String[] args) throws InterruptedException {
        // 休眠5s，保证开启偏向锁，也可以使用启动参数-XX:BiasedLockingStartupDelay=0来禁用延迟
        Thread.sleep(5000);
        Object obj = new Object();

        synchronized (obj){
            System.out.println("计算hashCode之前：偏向锁");
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            // 计算hashCode，它就无法进入偏向锁状态了
            System.out.println("hashCode值：" + obj.hashCode());
            System.out.println("计算hashCode之后：重量级锁");
            // 轻量级锁
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }

        System.out.println(9 > 9);

    }
}
