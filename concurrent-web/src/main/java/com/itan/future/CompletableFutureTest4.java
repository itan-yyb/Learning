package com.itan.future;

import org.apache.tomcat.jni.Poll;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * 计算完成时回调方法
 */
public class CompletableFutureTest4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        /**
         * exceptionally方法可以感知到异常，并且还能修改返回结果
         * 运行结果如下：
         * 当前线程：pool-1-thread-1
         * 运行结果：null ,异常：java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         * 返回结果：10
         */
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, pool).whenComplete((result, exception) -> {
            //虽然能获取异常信息和结果，但是无法修改返回结果
            System.out.println("运行结果：" + result + " ,异常：" + exception);
        }).exceptionally(exception -> {
            //可以感知异常，同时还能修改返回结果
            return 10;
        });
        System.out.println("返回结果：" + future1.get());

        /**
         * whenComplete方法虽然能获取异常信息和结果，但是无法修改返回结果，
         * 虽然在完成运算之后，对结果+3操作，但是最后get方法获取到的数据还是2
         * 运行结果如下：
         * 当前线程：pool-1-thread-1
         * 运行结果：2
         * 当前线程：main
         * 运行结果：5 ,异常：null
         * 当前线程：pool-1-thread-1
         * 运行结果：5 ,异常：null
         * 返回结果：2
         */
        // CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
        //     System.out.println("当前线程：" + Thread.currentThread().getName());
        //     int i = 10 / 5;
        //     System.out.println("运行结果：" + i);
        //     return i;
        // }, pool).whenComplete((result, exception) -> {
        //     //虽然能获取异常信息和结果，但是无法修改返回结果
        //     System.out.println("当前线程：" + Thread.currentThread().getName());
        //     result = result + 3;
        //     System.out.println("运行结果：" + result + " ,异常：" + exception);
        // }).whenCompleteAsync((result, exception) -> {
        //     //虽然能获取异常信息和结果，但是无法修改返回结果
        //     System.out.println("当前线程：" + Thread.currentThread().getName());
        //     result = result + 3;
        //     System.out.println("运行结果：" + result + " ,异常：" + exception);
        // }, pool);
        // System.out.println("返回结果：" + future2.get());
        //关闭线程池
        pool.shutdown();
    }
}
