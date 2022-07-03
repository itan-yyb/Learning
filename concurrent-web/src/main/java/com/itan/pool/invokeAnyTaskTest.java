package com.itan.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * invokeAny测试
 */
public class invokeAnyTaskTest {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列使用无界队列，拒绝策略为AbortPolicy
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        try {
            //接收返回值
            Integer result = pool.invokeAny(Arrays.asList(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " 处理任务 ");
                        Thread.sleep(3000);
                        return 1;
                    },
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " 处理任务 ");
                        Thread.sleep(1000);
                        return 2;
                    },
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " 处理任务 ");
                        Thread.sleep(500);
                        return 3;
                    }));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
/**
 * 运行结果如下：
 * pool-1-thread-1 处理任务
 * pool-1-thread-1 处理任务
 * 1
 */