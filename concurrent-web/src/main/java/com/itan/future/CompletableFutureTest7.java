package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * thenAccept方法测试
 */
public class CompletableFutureTest7 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        /**
         * thenAccept方法仅接收上一任务的返回结果，并消费，无返回结果；可以看到返回结果为null
         * 运行结果如下：
         * 当前线程：pool-1-thread-1
         * 运行结果：5
         * 当前线程：pool-1-thread-1
         * 上一步运行结果：5
         * 继续处理...
         * 返回结果：null
         */
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, pool).thenAcceptAsync(result -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("上一步运行结果：" + result);
            System.out.println("继续处理...");
        }, pool);
        System.out.println("返回结果：" + future1.get());
        //关闭线程池
        pool.shutdown();
    }
}
