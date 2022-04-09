package com.itan.nioweb.nio.channel;


import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/14
 */
public class Test {
    public static void main(String[] args) {
        // System.out.println("3 >> 2= " + (3 >> 2));
        // System.out.println(0b00000000000000000000000000000000);
        // System.out.println(3 / 2);
        // ArrayList list = new ArrayList(6);
        /**
         * 无符号右移：-3>>>2
         * 在进行位运算的时候要把数据转为二进制位，并且全部都是二进制补码形式！
         * 规则：数据位右移2位，左边空位都补0（包括符号位）,负数会变成正数
         * -3的原码： 0b10000000000000000000000000000011
         * -3的反码： 0b11111111111111111111111111111100
         * -3的补码： 0b11111111111111111111111111111101
         * -3>>>2：  0b00111111111111111111111111111111
         */
        // System.out.println("-3 >>> 2 = " + (-3 >>> 2));
        // System.out.println(0b00111111111111111111111111111111);
        HashMap<Object, String> map = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            map.put(i, i + "");
        }
        map.put("key","13");
    }
}
