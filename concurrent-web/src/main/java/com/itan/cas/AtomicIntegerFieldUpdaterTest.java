package com.itan.cas;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/14
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) throws InterruptedException {
        User user = new User();
        for (int i = 1; i <= 10000; i++) {
            int j = i;
            new Thread(() -> {
                // user.addScore(); //最后结果可能小于10000
                user.addScore(user);//最后结果为10000
            }, String.valueOf(i)).start();
        }
        Thread.sleep(5000);
        System.out.println("结果：" + user.score);
    }
}

class User {
    private String name;

    public volatile int score;

    AtomicIntegerFieldUpdater<User> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "score");

    public void addScore() {
        score = score + 1;
    }

    /**
     * 不加锁的方式，进行自增操作
     * @param user
     */
    public void addScore(User user) {
        fieldUpdater.incrementAndGet(user);
    }
}