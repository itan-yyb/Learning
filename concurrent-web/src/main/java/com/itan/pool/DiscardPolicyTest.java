package com.itan.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * 直接丢弃任务策略测试
 */
public class DiscardPolicyTest {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列容量设置为3，拒绝策略为DiscardOldestPolicy（丢弃最老任务，并提交当前任务）
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
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
 * 运行结果如下：首先任务0被核心线程直接执行，任务1,2,3进入队列；
 *             任务队列已满，线程数小于maximumPoolSize，任务4被救急线程执行；
 *             任务5进来后，正在运行的线程数达到maximumPoolSize，且队列已满，
 *             这时，直接将任务5丢弃，所以没有看到任务执行
 * pool-1-thread-1 处理任务 0
 * pool-1-thread-2 处理任务 4
 * pool-1-thread-1 处理任务 1
 * pool-1-thread-2 处理任务 2
 * pool-1-thread-1 处理任务 3
 */