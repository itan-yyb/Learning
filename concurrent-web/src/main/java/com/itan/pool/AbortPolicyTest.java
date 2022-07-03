package com.itan.pool;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * 直接抛出异常策略测试
 */
public class AbortPolicyTest {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列容量设置为3，拒绝策略为AbortPolicy（直接抛出异常）
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i < 6; i++) {
                final int no = i;
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 处理任务 " + no);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
