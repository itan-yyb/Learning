package com.itan.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/7
 * 1、验证不传入Executor时使用的线程池为ForkJoinPool
 * 2、验证传入Executor时使用自定义线程池
 */
public class CompletableFutureTest1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        //无返回值的
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("future1 start...");
            System.out.println(Thread.currentThread().getName());
            System.out.println("future1 end...");
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("future2 start...");
            System.out.println(Thread.currentThread().getName());
            System.out.println("future2 end...");
        }, pool);
        System.out.println(future1.get());//输出null
        System.out.println(future2.get());//输出null
        //关闭线程池
        pool.shutdown();
    }
}
/**
 * 运行结果如下：
 * future1 start...
 * ForkJoinPool.commonPool-worker-1
 * future1 end...
 * null
 * future2 start...
 * pool-1-thread-1
 * future2 end...
 * null
 */
