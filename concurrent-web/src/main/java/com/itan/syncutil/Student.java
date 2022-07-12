package com.itan.syncutil;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.Objects;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/12
 */
// @Data
@EqualsAndHashCode
public class Student {
    private Integer id;

    private String name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        int hashCode = new HashCodeBuilder(17, 37)
                .append(id).append(name)
                .toHashCode();
        System.out.println("hashCode值为：" + hashCode);
        return hashCode;
    }
}
