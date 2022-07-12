package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * thenRun方法测试
 */
public class ThenRunTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        /**
         * thenRun方法，不能获取上一步任务的执行结果，也无返回值
         * 运行结果如下：
         * 当前线程：pool-1-thread-1
         * 运行结果：5
         * 当前线程：pool-1-thread-1
         * 继续处理...
         * 返回结果：null
         */
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, pool).thenRunAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("继续处理...");
        }, pool);
        System.out.println("返回结果：" + future1.get());
        //关闭线程池
        pool.shutdown();
    }
}
