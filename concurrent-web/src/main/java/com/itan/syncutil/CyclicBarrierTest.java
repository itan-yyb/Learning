package com.itan.syncutil;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/8
 * 汇总用户数据
 */
@Slf4j
public class CyclicBarrierTest {
    //用于保存用户数据
    private static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

    private static AtomicInteger sum = new AtomicInteger(0);

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, new Thread(() -> {
            log.info("子任务全都执行完成，开始执行汇总结果...");
            sum.getAndAccumulate(map.values().stream().reduce(Integer::sum).get(), Integer::sum);
            log.info("子任务汇总结果为：" + sum.get());
        }));
        WorkTask workTask1 = new WorkTask(barrier, 500);
        WorkTask workTask2 = new WorkTask(barrier, 5000);
        WorkTask workTask3 = new WorkTask(barrier, 10000);
        workTask1.start();
        workTask2.start();
        workTask3.start();

    }

    /**
     * 工作线程
     */
    static class WorkTask extends Thread {
        //CyclicBarrier
        private CyclicBarrier barrier;

        //用户id
        private Integer userId;

        public WorkTask(CyclicBarrier barrier, Integer userId) {
            this.barrier = barrier;
            this.userId = userId;
        }

        @Override
        public void run() {
            try {
                int num = userId + 1000;
                log.info("用户 " + userId + " 线程开始统计数据，结果为：" + num);
                //统计不同用户数据
                map.put(userId, num);
                //模拟延迟
                Thread.sleep(num);
                barrier.await(-1, TimeUnit.NANOSECONDS);
                log.info("用户 " + userId + " 线程等待结束之后继续执行其他操作！！！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
