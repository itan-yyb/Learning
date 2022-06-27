package com.itan.queue;


import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/18
 * 优先级阻塞队列
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        //一个线程池，使用优先级的PriorityBlockingQueue阻塞队列作为任务队列
        ExecutorService pool = new ThreadPoolExecutor(0, 1, 1000, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        /*
         * 循环从priority = 0开始添加任务，即最先添加的任务优先级最低
         * 查看输出执行情况，可以发现最后添加的任务（优先级最高）最先被执行
         */
        for (int i = 0; i < 10; i++) {
            pool.execute(new ThreadTask(i));
        }
        pool.shutdown();
    }

    /**
     * 任务实现，同时作为Comparable的实现类
     */
    static class ThreadTask implements Runnable, Comparable<ThreadTask> {
        private int priority;

        ThreadTask(int priority) {
            this.priority = priority;
        }

        /**
         * 当前对象和其他对象做比较，当前priority大就返回-1，当前priority小就返回1,
         * @param o 被比较的线程任务
         * @return 返回-1就表示当前任务出队列优先级更高；返回1就表示当前任务出队列优先级更低；即priority值越大，出队列的优先级越高
         */
        @Override
        public int compareTo(ThreadTask o) {
            return this.priority > o.priority ? -1 : 1;
        }

        @Override
        public void run() {
            System.out.println("priority:" + this.priority + ",ThreadName:" + Thread.currentThread().getName());
        }
    }
}
