package com.itan.day1;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/12
 * 线程安全
 */
public class Ticket2 implements Runnable {
    private int num = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            ticket();
        }
    }

    public synchronized void ticket() {
        if (num <= 0) {
            flag = false;
            return;
        }
        //模拟网络延迟，放大问题发生的可能性
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num --;
        System.out.println(Thread.currentThread().getName() + " 抢到了，还有 " + num + "张票");
    }
}
