package com.itan.lock;

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
    }
}
