package com.itan;

import com.itan.syncutil.Student;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/12
 */
public class Test01 {
    @Test
    public void test1() {
        Student student1 = new Student(1, "张三");
        Student student2 = new Student(1, "张三");

        System.out.println(student1.equals(student2));
        Map<Student, String> hashMap = new HashMap<>();

        hashMap.put(student1, "123456");
        System.out.println("student1的hash：" + student1.hashCode());
        System.out.println("student2的hash：" + student2.hashCode());
        System.out.println(hashMap.get(student1));
        System.out.println(hashMap.get(student2));
    }
}
/**
 *
 */
