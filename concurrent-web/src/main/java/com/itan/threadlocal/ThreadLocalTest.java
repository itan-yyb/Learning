package com.itan.threadlocal;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/14
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        // 线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        List<String> list = Arrays.asList("张三", "李四", "王五", "赵六", "陈七");
        User user = new User();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    int bonus = new Random().nextInt(5000) + 4000;
                    user.addSalary(bonus);
                    System.out.println(Thread.currentThread().getName() + " 的工资为：" + user.threadLocal.get());
                } finally {
                    // 如果不清理自定义的ThreadLocal变量，可能会影响后续业务逻辑和造成内存泄露等问题
                    user.threadLocal.remove();
                }
            }, list.get(i)).start();
        }
        try {
            // for (int i = 0; i < 5; i++) {
            //     threadPool.submit(() -> {
            //         int bonus = new Random().nextInt(5000) + 4000;
            //         System.out.println(Thread.currentThread().getName() + " 初始值为：" + user.threadLocal.get());
            //         user.addSalary(bonus);
            //         System.out.println(Thread.currentThread().getName() + " 计算后的值为：" + user.threadLocal.get());
            //     }, threadPool);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

class User {
    // 工资
    int salary;

    // 初始化threadLocal
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 3000);

    public void addSalary(int bonus) {
        // 获取
        Integer basicSalary = threadLocal.get();
        salary = basicSalary + bonus;
        // 设置
        threadLocal.set(salary);
    }
}
/**
 * 运行结果如下：
 * 赵六 的工资为：8361
 * 张三 的工资为：9083
 * 王五 的工资为：9843
 * 李四 的工资为：7778
 * 陈七 的工资为：8191
 */

/**
 * 运行结果如下：
 */