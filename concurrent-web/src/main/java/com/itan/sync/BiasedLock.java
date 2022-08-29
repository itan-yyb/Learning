package com.itan.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/16
 * 测试偏向锁，设置JVM启动参数禁用延迟：-XX:BiasedLockingStartupDelay=0
 */
public class BiasedLock {
    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println("synchronized前");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj){
            System.out.println("synchronized中");
            // 偏向锁
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
        // 偏向锁
        System.out.println("synchronized后");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
