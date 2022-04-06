package com.itan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
    private Integer id;

    private String userName;

    private Integer age;
}
