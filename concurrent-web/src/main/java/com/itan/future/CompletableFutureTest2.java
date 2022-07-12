package com.itan.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/7
 * 测试有返回值的
 */
public class CompletableFutureTest2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        //有返回值的
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1 start...");
            System.out.println(Thread.currentThread().getName());
            System.out.println("future1 end...");
            return "future1 return";
        }, pool);
        System.out.println(future1.get());//future1 return
        //关闭线程池
        pool.shutdown();
    }
}
