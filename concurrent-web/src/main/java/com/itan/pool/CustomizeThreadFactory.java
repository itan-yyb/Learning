package com.itan.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/1
 * 自定义线程工厂
 */
public class CustomizeThreadFactory {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 100L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                //自定义线程工厂
                r -> {
                    System.out.println("创建 " + r.hashCode() + " 线程");
                    return new Thread(r,"customize-pool-" + r.hashCode());
                }, new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for (int i = 0; i < 6; i++) {
                final int no = i;
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 处理任务 " + no);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
/**
 * 运行结果：
 * 创建 1650967483 线程
 * 创建 87285178 线程
 * 创建 610998173 线程
 * customize-pool-1650967483 处理任务 0
 * customize-pool-87285178 处理任务 1
 * customize-pool-610998173 处理任务 5
 * customize-pool-610998173 处理任务 2
 * customize-pool-87285178 处理任务 4
 * customize-pool-1650967483 处理任务 3
 */