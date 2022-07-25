package com.itan.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date: 2022/7/16
 * 验证静态同步方法，虽然每个线程使用不同new对象调用方法，但是只要有一个进入静态同步方法，
 * 其他线程都不能进入任何静态同步方法，因为锁对象为资源类的Class对象（即User3.Class）
 */
public class Sync3 {
    public static void main(String[] args) {
        //使用两个资源类，分别调用不同的方法
        User3 user = new User3();
        User3 user1 = new User3();
        new Thread(() -> {
            user.getUser();
        }, "a").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            user1.getOne();
        }, "b").start();
    }
}

/**
 * @Date: 2022/7/16
 * 资源类
 */
class User3 {
    protected static Logger logger = LoggerFactory.getLogger(User3.class);

    public static synchronized void getUser() {
        logger.info("getUser开始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("getUser结束");
    }

    public static synchronized void getList() {
        logger.info("getList开始");
    }

    public void getOne() {
        logger.info("进入普通方法getOne");
    }
}
/**
 * 运行结果如下：先打印静态同步方法getUser，再打印出静态同步方法getList
 * 17:30:17.009 [a] INFO com.itan.sync.User3 - getUser开始
 * 17:30:20.019 [a] INFO com.itan.sync.User3 - getUser结束
 * 17:30:20.020 [b] INFO com.itan.sync.User3 - getList开始
 */