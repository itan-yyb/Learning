package com.itan.queue;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/14
 * ArrayBlockingQueue实现生产者消费者
 */
@Slf4j
public class ArrayBlockingQueueTest {
    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10, true);
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
    }
}
