package com.itan.sync;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author: ye.yanbin
 * @Date: 2022/8/16
 */
public class JOLDemo {
    public static void main(String[] args) {
        Object object = new Object();

        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}
/**
 * 运行结果：
 * # Running 64-bit HotSpot VM.
 * # Using compressed oop with 3-bit shift.
 * # Using compressed klass with 3-bit shift.
 * # Objects are 8 bytes aligned.
 * # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
 * # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
 *
 * 8
 */