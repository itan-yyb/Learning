package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * thenApply方法测试
 */
public class ThenApplyTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        /**
         * 运行结果如下
         * 当前线程：pool-1-thread-1
         * 运行结果：5
         * 当前线程：pool-1-thread-1
         * 上一步运行结果：5
         * 返回结果：hello5
         */
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, pool).thenApplyAsync(result -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("上一步运行结果：" + result);
            return "hello" + result;
        }, pool);
        System.out.println("返回结果：" + future1.get());
        //关闭线程池
        pool.shutdown();
    }
}
