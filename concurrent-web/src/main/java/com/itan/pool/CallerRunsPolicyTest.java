package com.itan.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * 调用者线程运行拒绝任务的测试
 */
public class CallerRunsPolicyTest {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列容量设置为3，拒绝策略为CallerRunsPolicy（调用者线程运行拒绝任务）
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
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
/**
 * 运行结果如下：可以看到任务5被main线程直接运行
 * main 处理任务 5
 * pool-1-thread-2 处理任务 4
 * pool-1-thread-1 处理任务 0
 * pool-1-thread-2 处理任务 1
 * pool-1-thread-1 处理任务 2
 * pool-1-thread-2 处理任务 3
 */