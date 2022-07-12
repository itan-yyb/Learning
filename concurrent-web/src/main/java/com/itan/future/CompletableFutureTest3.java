package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 */
public class CompletableFutureTest3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        /**
         * get方法，阻塞式获取结果
         * join方法，阻塞式获取结果
         */
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "完成";
        }, pool);
        System.out.println("get方法：" + future1.get());
        System.out.println("join方法：" + future1.join());
        /**
         * 带有超时的get方法，超过设定的时间，没有返回值，直接返回null
         */
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "完成";
        }, pool);
        System.out.println(future2.get(2L, TimeUnit.SECONDS));
        /**
         * 立即获取结果（不阻塞），如果完成则返回结果值，否则返回给定的valueIfAbsent
         */
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "完成";
        }, pool);
        System.out.println(future3.getNow("自定义getNow的值"));
        /**
         * 是否打断get方法，立即返回自定义的值
         */
        System.out.println(future3.complete("自定义complete的值") + "\t结果为：" + future3.join());
        //关闭线程池
        pool.shutdown();
    }
}
