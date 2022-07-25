package com.itan.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date: 2022/7/16
 * 验证一个类是静态的
 */
public class Sync5 {
    public static void main(String[] args) {
        //使用两个资源类，分别调用不同的方法
        User4 user = new User4();
        User4 user1 = new User4();
        new Thread(() -> {
            user.getUser();
        }, "a").start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            // user.getList();
            user1.getList();
        }, "b").start();
    }
    /**
     * @Date: 2022/7/16
     * 资源类
     */
    static class User5 {
        protected static Logger logger = LoggerFactory.getLogger(User5.class);

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

}
/**
 * 运行结果如下：先打印静态同步方法内容：getUser开始，再立刻打印出普通同步方法内容，3s后静态同步方法执行结束
 * 17:46:36.166 [b] INFO com.itan.sync.User4 - getList开始
 * 17:46:36.166 [a] INFO com.itan.sync.User4 - getUser开始
 * 17:46:39.179 [a] INFO com.itan.sync.User4 - getUser结束
 */