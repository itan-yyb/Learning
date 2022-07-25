package com.itan.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/16
 * 验证某一个线程先访问同步方法后，其他线程访问其他同步方法需要等待，
 * 说明锁的是当前对象this，也就是代码中的new User1()这个实例
 */
public class Sync1 {
    public static void main(String[] args) {
        User1 user = new User1();
        User1 user2 = new User1();
        new Thread(() -> {
            user.getUser();
        }, "a").start();

        new Thread(() -> {
            user2.getList();
        }, "b").start();
    }
}

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/16
 * 资源类
 */
class User1 {
    protected Logger logger = LoggerFactory.getLogger(User1.class);

    public synchronized void getUser() {
        logger.info("进入getUser方法");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("退出getUser方法");
    }

    public synchronized void getList() {
        logger.info("进入getList方法");
    }
}
/**
 * 运行结果如下：先打印出getUser方法内容，3秒过后打印出getList方法内容，
 * 说明其他线程再去访问其他同步方法的时候需要等待
 * 16:58:52.830 [a] INFO com.itan.sync.User - 进入getUser方法
 * 16:58:55.848 [b] INFO com.itan.sync.User - 进入getList方法
 */