package com.itan.cas;

import java.util.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/19
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(list);
            }, "第" + i + "个线程").start();
        }
    }
}
