package com.itan.syncutil;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/11
 * 停车场占用车位，每走一辆才会停一辆
 */
@Slf4j
public class SemaphoreTest {
    public static void main(String[] args) {
        //初始化一个信号量为3，默认是false非公平锁， 模拟3个停车位
        Semaphore semaphore = new Semaphore(3);
        //模拟多台车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    //进入车位占用
                    semaphore.acquire();
                    log.info("车辆 " + Thread.currentThread().getName() + " 抢到车位");
                    //停车3s
                    TimeUnit.SECONDS.sleep(3);
                    //释放
                    semaphore.release();
                    log.info("车辆 " + Thread.currentThread().getName() + " 离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, i + "").start();
        }
    }
}
