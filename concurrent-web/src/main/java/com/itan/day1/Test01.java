package com.itan.day1;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/12
 */
public class Test01 {
    public static void main(String[] args) {
        //一份资源
        Ticket1 ticket = new Ticket1();
        //多个使用
        new Thread(ticket, "A").start();
        new Thread(ticket, "B").start();
        new Thread(ticket, "C").start();
    }
}
