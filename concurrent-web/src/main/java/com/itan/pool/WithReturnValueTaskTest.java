package com.itan.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/2
 * 带有返回值任务测试
 */
public class WithReturnValueTaskTest {
    public static void main(String[] args) {
        //corePoolSize设置为1，maximumPoolSize设置为2，队列使用无界队列，拒绝策略为AbortPolicy
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //接收返回值
        List<Future<?>> futures = new ArrayList<>();
        try {
            for (int i = 0; i < 3; i++) {
                final int no = i;
                Future<Integer> future = pool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + " 处理任务 " + no);
                    return no;
                });
                futures.add(future);
            }
            //查看结果
            for (Future<?> future : futures) {
                //get方法会阻塞主线程
                System.out.println("返回结果：" + future.get());
            }
            System.out.println("执行完成...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
/**
 * 运行结果如下：
 * pool-1-thread-1 处理任务 0
 * 返回结果：0
 * pool-1-thread-1 处理任务 1
 * 返回结果：1
 * pool-1-thread-1 处理任务 2
 * 返回结果：2
 * 执行完成...
 */