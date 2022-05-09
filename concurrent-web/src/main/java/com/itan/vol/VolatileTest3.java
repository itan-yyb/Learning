package com.itan.vol;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/27
 * 使用synchronized解决可见性
 */
@Slf4j
public class VolatileTest3 {
    final static Object obj = new Object();

    static boolean status = false;

    public static void main(String[] args) {
        log.info("线程{}开始执行，status = {}", Thread.currentThread().getName() ,status);
        new Thread(()->{
            synchronized (obj) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将值修改为true
                status = true;
                log.info("线程{}修改status，status = {}", Thread.currentThread().getName(), status);
            }
        }, "A").start();
        while (true) {
            synchronized (obj) {
                if (status) {
                    break;
                }
            }
        }
        log.info("线程{}，status = {}", Thread.currentThread().getName() ,status);
    }
}
