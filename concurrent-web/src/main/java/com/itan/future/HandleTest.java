package com.itan.future;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/10
 * handle方法测试
 */
public class HandleTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        /**
         * 能对异常和返回结果处理
         * 运行结果如下：
         * 当前线程：pool-1-thread-1
         * 运行结果：2
         * 当前线程：main
         * 上一步运行结果：2 异常信息：null
         * 返回结果：4
         */
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 4;
            System.out.println("运行结果：" + i);
            return i;
        }, pool).handle((result, exception) -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("上一步运行结果：" + result + " 异常信息：" + exception);
            if (result != null) {
                return result * 2;
            }
            if (exception != null) {
                return 0;
            }
            return 0;
        });
        System.out.println("返回结果：" + future1.get());
        //关闭线程池
        pool.shutdown();
    }
}
