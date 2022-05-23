package com.itan.cas;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/19
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 6));
                System.out.println(map);
            }, "第" + i + "个线程").start();
        }
    }
}
