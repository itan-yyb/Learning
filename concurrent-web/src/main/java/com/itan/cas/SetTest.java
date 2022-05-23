package com.itan.cas;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/19
 */
public class SetTest {
    public static void main(String[] args) {
        Set<String> set = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(set);
            }, "第" + i + "个线程").start();
        }
    }
}
