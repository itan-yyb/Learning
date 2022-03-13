package com.itan.nioweb.bio.four;

import java.util.concurrent.*;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 线程池
 */
public class ThreadPool {
    private static ThreadPoolExecutor threadPool = null;

    /**
     * 获取线程池
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (null == threadPool) {
            synchronized (ThreadPool.class) {
                if (null == threadPool) {
                    threadPool = new ThreadPoolExecutor(3, 5, 120L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(10));
                }
            }
        }
        return threadPool;
    }
}
