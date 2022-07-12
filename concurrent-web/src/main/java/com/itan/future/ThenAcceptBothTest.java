package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * thenAcceptBoth方法测试
 * 组合两个任务，能获取两个任务的返回值，没有返回结果，需要前两个任务都执行完成才能触发
 */
public class ThenAcceptBothTest {
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
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return str;
        }, pool);

        //组合两任务
        CompletableFuture<Void> future3 = future1.thenAcceptBothAsync(future2, (f1, f2) -> {
            System.out.println("任务3开始...任务1的结果：" + f1 + " 任务2的结果：" + f2);
        }, pool);
        System.out.println("返回结果：" + future3.get());
        //关闭线程池
        pool.shutdown();
    }
}
/**
 * 运行结果如下：
 * 任务1开始：pool-1-thread-1
 * 任务1结束：5
 * 任务2开始：pool-1-thread-2
 * 任务2结束：hello
 * 任务3开始...任务1的结果：5 任务2的结果：hello
 * 返回结果：null
 */