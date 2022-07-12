package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * AcceptEither方法测试
 * 两个任务，只要有一个完成，就执行任务3，获取执行完成的任务的结果，无返回值
 */
public class AcceptEitherTest {
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
            System.out.println("任务2结束：" + str);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return str;
        }, pool);

        //组合两任务
        CompletableFuture<Void> future3 = future1.acceptEitherAsync(future2, (res) -> {
            System.out.println("任务3开始...之前的任务的结果：" + res);
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
 * 任务3开始...之前的任务的结果：5
 * 返回结果：null
 */