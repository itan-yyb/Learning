package com.itan.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/13
 * 阻塞队列：第一组
 */
@Slf4j
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        log.info("插入状态：" + queue.offer("a", 2L, TimeUnit.SECONDS));
        log.info("插入状态：" + queue.offer("b", 2L, TimeUnit.SECONDS));
        log.info("插入状态：" + queue.offer("c", 2L, TimeUnit.SECONDS));
        log.info("插入状态：" + queue.offer("d", 2L, TimeUnit.SECONDS));//会阻塞2秒，然后退出
    }
}
/**
 * 00:29:58.918 [main] INFO com.itan.queue.BlockingQueueTest - 插入状态：true
 * 00:29:58.923 [main] INFO com.itan.queue.BlockingQueueTest - 插入状态：true
 * 00:29:58.923 [main] INFO com.itan.queue.BlockingQueueTest - 插入状态：true
 * 00:30:00.929 [main] INFO com.itan.queue.BlockingQueueTest - 插入状态：false
 */