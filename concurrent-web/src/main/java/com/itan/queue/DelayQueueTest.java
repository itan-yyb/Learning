package com.itan.queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/19
 * 延时阻塞队列
 */
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //创建DelayQueue队列
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
        //创建并添加10个任务
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            //每个任务的延迟时间在[0,1000)之间
            DelayTask task = new DelayTask(random.nextInt(1000), "task " + (i + 1));
            delayQueue.add(task);
        }
        //循环获取过期任务并打印，可以看到结果就是延迟时间最短的任务最先出队，这和任务添加的顺序无关
        while (true) {
            System.out.println(delayQueue.take());
            if (delayQueue.isEmpty()) {
                break;
            }
        }
    }

    static class DelayTask implements Delayed {
        private final long delayTime; //延迟时间
        private final long expire; //到期时间
        private String taskName; //任务名称

        /**
         * 构造器
         * @param delayTime 延迟时间，毫秒
         * @param taskName  任务名称
         */
        DelayTask(long delayTime, String taskName) {
            this.delayTime = delayTime;
            //计算出到期时间点 -> 延迟时间 + 当前时间
            this.expire = delayTime * 100 + System.currentTimeMillis();
            this.taskName = taskName;
        }

        /**
         * 在调用DelayQueue的出队列的时候会调用该方法并传递参数NANOSECONDS
         * @param unit 时间单位，
         * @return 返回剩余延迟时间纳秒 -> 到期时间－当前时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 用于加入队列时元素之间比较构建小顶堆的方法
         * @param o 参数对象
         * @return 如果返回负数，表示this对象小于，那么this对象将比参数对象优先出队列
         */
        @Override
        public int compareTo(Delayed o) {
            //比较this对象和参数对象剩余时间长短
            //this对象剩余时间大于参数对象剩余时间，则返回大于0。那么将会设置this对象的出队列优先级小于当前对象
            //this对象剩余时间小于参数对象剩余时间，则返回小于0。那么将会设置this对象的出队列优先级大于当前对象
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
        @Override
        public String toString() {
            return "DelayedEle{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }
    }
}
