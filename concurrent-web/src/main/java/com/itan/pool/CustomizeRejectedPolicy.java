package com.itan.pool;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * 自定义拒绝策略
 */
public class CustomizeRejectedPolicy {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列容量设置为3，拒绝策略为DiscardOldestPolicy（丢弃最老任务，并提交当前任务）
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(),
                //自定义拒绝策略
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " 执行了拒绝策略");
                    }
                });
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
 * 运行结果：
 * com.itan.pool.CustomizeRejectedPolicy$$Lambda$1/2108649164@21b8d17c 执行了拒绝策略
 * pool-1-thread-2 处理任务 4
 * pool-1-thread-1 处理任务 0
 * pool-1-thread-2 处理任务 1
 * pool-1-thread-1 处理任务 2
 * pool-1-thread-2 处理任务 3
 */