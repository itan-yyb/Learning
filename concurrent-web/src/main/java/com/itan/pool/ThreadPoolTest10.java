package com.itan.pool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/1
 * 优先级队列测试
 */
public class ThreadPoolTest10 {
    public static void main(String[] args) {
        //corePoolSize设置为0，目的是防止第一个任务被直接执行
        //maximumPoolSize设置为2，拒绝策略为AbortPolicy，直接抛出异常
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 2, 100L, TimeUnit.SECONDS,
                            new PriorityBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        try {
            //循环从priority=0开始添加任务，即最先添加的任务优先级最低
            for (int i = 0; i < 6; i++) {
                pool.execute(new RunnableTask(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    /**
     * 需要实现Comparable接口，可比较大小
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class RunnableTask implements Runnable, Comparable<RunnableTask> {
        //任务优先级大小
        private Integer priority;

        /**
         * 当前对象和其他对象做比较，当前优先级大就返回-1，优先级小就返回1，值越小优先级越高
         * @param o
         * @return
         */
        @Override
        public int compareTo(RunnableTask o) {
            return this.priority > o.priority ? -1 : 1;
        }

        @Override
        public void run() {
            //让线程阻塞，使后续任务进入缓存队列
            try {
                Thread.sleep(1000L);
                System.out.println("priority " + this.priority + " ThreadName " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * 运行结果：可以发现最后添加的任务（优先级最高）最先被执行
 * priority 5 ThreadName pool-1-thread-1
 * priority 4 ThreadName pool-1-thread-1
 * priority 3 ThreadName pool-1-thread-1
 * priority 2 ThreadName pool-1-thread-1
 * priority 1 ThreadName pool-1-thread-1
 * priority 0 ThreadName pool-1-thread-1
 */