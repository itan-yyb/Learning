package com.itan.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/27
 * 线程池
 */
@Slf4j
public class ThreadPoolTest4 {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        //添加5个任务，延迟1s之后执行
        for (int i = 0; i < 5; i++) {
            final int no = i;
            log.info("thread " + no + " start");
            pool.schedule(() -> {
                log.info("thread " + no + " execute");
            }, 1000, TimeUnit.MILLISECONDS);
        }
        pool.shutdown();
    }
}
/**
 * 运行结果如下：
 * 10:58:12.474 [main] INFO com.itan.pool.ThreadPoolTest4 - thread 0 start
 * 10:58:12.670 [main] INFO com.itan.pool.ThreadPoolTest4 - thread 1 start
 * 10:58:12.670 [main] INFO com.itan.pool.ThreadPoolTest4 - thread 2 start
 * 10:58:12.670 [main] INFO com.itan.pool.ThreadPoolTest4 - thread 3 start
 * 10:58:12.670 [main] INFO com.itan.pool.ThreadPoolTest4 - thread 4 start
 * 10:58:13.675 [pool-1-thread-1] INFO com.itan.pool.ThreadPoolTest4 - thread 0 execute
 * 10:58:13.676 [pool-1-thread-1] INFO com.itan.pool.ThreadPoolTest4 - thread 1 execute
 * 10:58:13.676 [pool-1-thread-1] INFO com.itan.pool.ThreadPoolTest4 - thread 2 execute
 * 10:58:13.676 [pool-1-thread-1] INFO com.itan.pool.ThreadPoolTest4 - thread 3 execute
 * 10:58:13.676 [pool-1-thread-1] INFO com.itan.pool.ThreadPoolTest4 - thread 4 execute
 */