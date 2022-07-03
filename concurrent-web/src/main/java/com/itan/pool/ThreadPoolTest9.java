package com.itan.pool;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/1
 * 无界队列测试
 */
public class ThreadPoolTest9 {
    public static void main(String[] args) {
        //maximumPoolSize设置为2，拒绝策略为AbortPolicy，直接抛出异常
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
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
 * 运行结果：并没有创建救急线程来运行后面的任务
 * pool-1-thread-1 处理任务 0
 * pool-1-thread-1 处理任务 1
 * pool-1-thread-1 处理任务 2
 * pool-1-thread-1 处理任务 3
 * pool-1-thread-1 处理任务 4
 * pool-1-thread-1 处理任务 5
 */