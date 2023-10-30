package com.cc.lab03cachecaffeine01.model;

import lombok.Data;
import lombok.ToString;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-10-30 18:36
 */
@Data
@ToString
public class UserInfo {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
}
