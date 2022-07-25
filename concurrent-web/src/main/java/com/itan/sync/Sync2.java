package com.itan.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date: 2022/7/16
 * 验证一个线程先访问同步方法，其他线程访问普通方法不需要等待
 */
public class Sync2 {
    public static void main(String[] args) {
        User2 user = new User2();
        new Thread(() -> {
            user.getUser();
        }, "a").start();

        new Thread(() -> {
            user.getOne();
        }, "b").start();
    }
}

/**
 * @Date: 2022/7/16
 * 资源类
 */
class User2 {
    protected Logger logger = LoggerFactory.getLogger(User2.class);

    public synchronized void getUser() {
        logger.info("getUser开始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("getUser结束");
    }

    public void getOne() {
        logger.info("进入普通方法getOne");
    }
}
/**
 * 运行结果如下：先打印同步方法内容，再立刻打印出普通方法内容，3s后同步方法执行结束
 * 17:12:38.678 [a] INFO com.itan.sync.User2 - getUser开始
 * 17:12:38.678 [b] INFO com.itan.sync.User2 - 进入普通方法getOne
 * 17:12:41.696 [a] INFO com.itan.sync.User2 - getUser结束
 */