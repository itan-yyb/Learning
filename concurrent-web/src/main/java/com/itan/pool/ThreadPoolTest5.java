package com.itan.pool;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ye.yanbin
 * @Date: 2022/6/27
 * 线程池--定时执行
 */
@Slf4j
public class ThreadPoolTest5 {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //添加两个任务，希望它们都在1s后执行
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        System.out.println(sdf.format(new Date()) + " " +  Thread.currentThread().getName() + " - start");
        //创建并执行一个周期性动作，该动作在给定的初始延迟后首先启用，随后在给定的周期内启用
        //参数说明：任务对象，初始延迟，延迟间隔，时间单位
        // pool.scheduleAtFixedRate(()->{
        //     try {
        //         //如果此任务的任何执行时间超过其周期，则后续执行可能会延迟开始
        //         Thread.sleep(3000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        //     System.out.println(sdf.format(new Date()) + " " + Thread.currentThread().getName() + " - running...");
        // },1000,1000, TimeUnit.MILLISECONDS);

        //创建并执行一个周期性操作，该操作首先在给定的初始延迟之后启用，随后在一个执行的终止和下一个执行的开始之间具有给定的延迟
        //参数说明：任务对象，初始延迟，延迟间隔，时间单位
        // pool.scheduleWithFixedDelay(()->{
        //     System.out.println(sdf.format(new Date()) + " " + Thread.currentThread().getName() + " - running...");
        //     try {
        //         //程序会等待一秒，再加上间隔一秒，每次执行将间隔两秒
        //         Thread.sleep(2000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // },1000,1000, TimeUnit.MILLISECONDS);

        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //获取11:51:00
        LocalDateTime time = now.withHour(11).withMinute(51).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        //如果当前时间 > 开始执行的时间，就往后一天
        if (now.compareTo(time) > 0){
            time = time.plusDays(1);
        }
        System.out.println(time);
        //计算当前时间和要执行任务的时间差
        long initailDelay = Duration.between(now, time).toMillis();
        //计算一天的间隔时间
        long period = 1000 * 60 * 60 * 24 * 1;
        pool.scheduleAtFixedRate(()->{
            System.out.println("running");
        },initailDelay, period, TimeUnit.MILLISECONDS);
    }
}
/**
 * 运行结果如下：
 * 11:52:18 main - start
 * 2022-07-01T11:51
 */