package com.itan.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/16
 * 轻量级锁，设置JVM启动参数关闭偏向锁：-XX:-UseBiasedLocking
 */
public class LightWeightLock {
    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println("synchronized前");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj){
            System.out.println("synchronized中");
            // 轻量级锁
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
        // 轻量级锁
        System.out.println("synchronized后");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
