package com.itan.vol;

/**
 * @Author: ye.yanbin
 * @Date: 2022/5/5
 * 指令重排序
 */
public class VolatileTest6 {
    int a = 0;
    boolean flag = false;

    //线程2执行
    public void method1() {
        a = 1;
        //此处可能会发生指令重排序，可能先执行a=1也可能先执行flag=true
        flag = true;
    }

    //线程1执行
    public void method2() {
        if (flag) {
            a = a + 5;
            System.out.println("a的值为：" + a);
        } else {
            a = 1;
            System.out.println("a的值为：" + a);
        }
    }
}
