package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * runAfterBoth方法测试
 * 组合两个任务，无法获取前两个任务的返回值，也没有返回结果，需要前两个任务都执行完成才能触发
 */
public class RunAfterBothTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1开始：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("任务1结束：" + i);
            return i;
        }, pool);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2开始：" + Thread.currentThread().getName());
            String str = "hello";
            System.out.println("任务2结束：" + str);
            return str;
        }, pool);

        //组合两任务
        CompletableFuture<Void> future3 = future1.runAfterBothAsync(future2, () -> {
            System.out.println("任务3开始：" + Thread.currentThread().getName());
        }, pool);
        System.out.println("返回结果：" + future3.get());
        //关闭线程池
        pool.shutdown();
    }
}
