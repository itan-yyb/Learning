package com.itan.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * invokeAll执行所有任务测试
 */
public class invokeAllTaskTest {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列使用无界队列，拒绝策略为AbortPolicy
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        try {
            //接收返回值
            List<Future<Integer>> futures = pool.invokeAll(Arrays.asList(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " 处理任务 ");
                        return 1;
                    },
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " 处理任务 ");
                        return 2;
                    },
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " 处理任务 ");
                        return 3;
                    }));
            //查看结果
            for (Future<Integer> future : futures) {
                //get方法会阻塞主线程
                System.out.println("返回结果：" + future.get());
            }
            System.out.println("执行完成...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            pool.shutdown();
        }
    }
}
/**
 * 运行结果如下：
 * pool-1-thread-1 处理任务
 * pool-1-thread-1 处理任务
 * pool-1-thread-1 处理任务
 * 返回结果：1
 * 返回结果：2
 * 返回结果：3
 * 执行完成...
 */