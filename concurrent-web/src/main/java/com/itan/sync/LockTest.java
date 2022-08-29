package com.itan.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/16
 * 验证当一个对象计算过hashcode时，它就无法进入到偏向锁状态，
 * 跳过偏向锁，直接升级为轻量级锁
 */
public class LockTest {
    public static void main(String[] args) throws InterruptedException {
        // 休眠5s，保证开启偏向锁，也可以使用启动参数-XX:BiasedLockingStartupDelay=0来禁用延迟
        Thread.sleep(5000);
        Object obj = new Object();
        System.out.println("偏向锁信息：");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        // 计算hashCode，它就无法进入偏向锁状态了
        System.out.println("hashCode值：" + obj.hashCode());
        synchronized (obj){
            System.out.println("轻量级锁信息：");
            // 轻量级锁
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
    }
}
