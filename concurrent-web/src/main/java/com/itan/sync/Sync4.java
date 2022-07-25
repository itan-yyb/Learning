package com.itan.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date: 2022/7/16
 * 验证既有静态同步方法，又有普通同步方法情况下，调用方法互不干扰，因为锁对象不同，
 * 静态同步方法锁对象是类Class对象，普通同步方法锁对象为实例对象（new XX()）
 */
public class Sync4 {
    public static void main(String[] args) {
        //使用两个资源类，分别调用不同的方法
        User4 user = new User4();
        User4 user1 = new User4();
        new Thread(() -> {
            user.getUser();
        }, "a").start();

        new Thread(() -> {
            // user.getList();
            user1.getList();
        }, "b").start();
    }
}

/**
 * @Date: 2022/7/16
 * 资源类
 */
class User4 {
    protected static Logger logger = LoggerFactory.getLogger(User4.class);

    public static synchronized void getUser() {
        logger.info("getUser开始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("getUser结束");
    }

    public synchronized void getList() {
        logger.info("getList开始");
    }
}
/**
 * 运行结果如下：先打印静态同步方法内容：getUser开始，再立刻打印出普通同步方法内容，3s后静态同步方法执行结束
 * 17:46:36.166 [b] INFO com.itan.sync.User4 - getList开始
 * 17:46:36.166 [a] INFO com.itan.sync.User4 - getUser开始
 * 17:46:39.179 [a] INFO com.itan.sync.User4 - getUser结束
 */