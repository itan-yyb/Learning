package com.itan.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/3
 * 1、创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。
 * 2、创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。
 * 3、使用FutureTask对象作为Thread对象的target创建并启动新线程。
 * 4、调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。
 */
@Slf4j
public class FutureTest1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info(Thread.currentThread().getName() + " 开始执行...");
        //实例化Callable
        CallTest callTest = new CallTest();
        //使用FutureTask类来包装Callable对象
        FutureTask<String> task = new FutureTask<>(callTest);
        //使用FutureTask对象作为Thread对象的target创建新线程
        Thread thread = new Thread(task, "异步线程1");
        //启动线程
        thread.start();
        //调用FutureTask对象的get()方法来获得子线程执行结束后的返回值
        log.info("task返回结果 " + task.get());
        log.info(Thread.currentThread().getName() + " 结束运行...");
    }

    /**
     * 创建Callable接口的实现类，并实现call()方法
     */
    static class CallTest implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(5000);
            return Thread.currentThread().getName() + " 执行call方法，执行耗时5s";
        }
    }
}
/**
 * 23:55:33.618 [main] INFO com.itan.future.FutureTest1 - main 开始执行...
 * 23:55:38.679 [main] INFO com.itan.future.FutureTest1 - task返回结果 异步线程1 执行call方法，执行耗时5s
 * 23:55:38.679 [main] INFO com.itan.future.FutureTest1 - main 结束运行...
 */