package com.itan.pool;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/1
 * 直接提交队列测试
 */
public class ThreadPoolTest7 {
    public static void main(String[] args) {
        //maximumPoolSize设置为2，拒绝策略为AbortPolicy，直接抛出异常
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                            new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
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
 * pool-1-thread-2 处理任务 1
 * pool-1-thread-1 处理任务 0
 * java.util.concurrent.RejectedExecutionException: Task com.itan.pool.ThreadPoolTest7$$Lambda$1/777874839@6433a2 rejected from java.util.concurrent.ThreadPoolExecutor@5910e440[Running, pool size = 2, active threads = 2, queued tasks = 0, completed tasks = 0]
 * 	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
 * 	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
 * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
 * 	at com.itan.pool.ThreadPoolTest7.main(ThreadPoolTest7.java:18)
 */