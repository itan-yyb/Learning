package com.itan.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/27
 * 线程池
 */
public class ThreadPoolTest2 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int no = i;
            pool.execute(() -> {
                System.out.println("Thread " + no + " Start");
                try {
                    Thread.sleep(no * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + no + " End");
            });
        }
        pool.shutdown();
    }
}
/**
 * 运行结果如下：
 * Thread 0 Start
 * Thread 0 End
 * Thread 1 Start
 * Thread 1 End
 * Thread 2 Start
 * Thread 2 End
 * Thread 3 Start
 * Thread 3 End
 * Thread 4 Start
 * Thread 4 End
 */