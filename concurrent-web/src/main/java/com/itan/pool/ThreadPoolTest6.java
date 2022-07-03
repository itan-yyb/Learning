package com.itan.pool;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/27
 * 2个核心线程，阻塞队列长度为3，验证后进来的任务，会被救急线程先执行
 */
@Slf4j
public class ThreadPoolTest6 {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 100L, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i < 6; i++) {
                final int no = i;
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 处理任务 " + no);
                    try {
                        Thread.sleep(4000);
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
 * 结果分析：首先处理0,1两个任务，2,3,4任务进入队列中，当5号任务进来时候，发现没有空闲线程且队列已满
 *          则创建救急线程并且立刻执行当前任务，所以看到5号任务在2,3,4之前执行
 * pool-1-thread-1 处理任务 0
 * pool-1-thread-3 处理任务 5
 * pool-1-thread-2 处理任务 1
 * pool-1-thread-1 处理任务 2
 * pool-1-thread-2 处理任务 3
 * pool-1-thread-3 处理任务 4
 */