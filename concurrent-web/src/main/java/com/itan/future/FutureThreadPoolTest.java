package com.itan.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/3
 * 结合线程池运行
 */
@Slf4j
public class FutureThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        //corePoolSize设置为1，maximumPoolSize设置为2，队列容量设置为3，拒绝策略为AbortPolicy（直接抛出异常）
        ExecutorService pool = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            Thread.sleep(300);
            return "任务1结束";
        });
        pool.submit(futureTask1);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            Thread.sleep(500);
            return "任务2结束";
        });
        pool.submit(futureTask2);
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        Thread.sleep(300);
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start) + " 毫秒");
        pool.shutdown();
    }
}
/**
 * 运行结果：
 * 任务1结束
 * 任务2结束
 * 总耗时：989 毫秒
 */