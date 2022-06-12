package com.itan.lock;

import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/23
 */
public class LockTest {
    public static void main(String[] args) {
        /**
         * 创建一个可重入锁，true表示公平锁，false表示非公平锁。默认非公平锁（空参）
         */
        Lock lock = new ReentrantLock(true);
        String s = "02.fastqMo5411.fastq";
        // String s1 = s.substring(0, s.lastIndexOf("."));
        // String s2 = StringUtils.substringAfterLast(s, ".");
        // System.out.println(s1);

        StringBuilder builder = new StringBuilder(s);
        builder.insert(s.lastIndexOf("."), "_optime");
        System.out.println(builder);
    }
}
