package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * AllOf方法测试
 * 多任务组合，等待所有任务完成
 */
public class AllOfTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1开始：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("任务1结束：" + i);
            return i;
        }, pool);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2开始：" + Thread.currentThread().getName());
            String str = "hello";
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2结束：" + str);
            return str;
        }, pool);

        CompletableFuture<Object> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务3开始：" + Thread.currentThread().getName());
            return "成功";
        }, pool);
        //多任务组合
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        allOf.get();

        System.out.println("main线程执行完成");
        //关闭线程池
        pool.shutdown();
    }
}
/**
 * 运行结果如下：
 * 任务1开始：pool-1-thread-1
 * 任务1结束：5
 * 任务2开始：pool-1-thread-2
 * 任务3开始：pool-1-thread-3
 * 任务2结束：hello
 * main线程执行完成
 */