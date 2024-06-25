package com.cc.lab9901.test03.demo01;

import java.util.Optional;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 16:32
 */
public class OptionalDemo {
    public static void main(String[] args) {
        //Optional https://mp.weixin.qq.com/s/WL0g_U1nT3gmDuY7lO4CMA
        traditionalOptional();
        lambdaOptional();
    }

    public static void traditionalOptional() {
        // 创建一个Optional对象
        Optional<String> optional = Optional.of("hello");
        // 使用传统方式检查并打印值
        if (optional.isPresent()) {
            System.out.println("传统方式Optional值: " + optional.get());
        }
    }

    public static void lambdaOptional() {
        // 创建一个Optional对象
        Optional<String> optional = Optional.of("hello");
        // 使用Lambda表达式检查并打印值
        optional.ifPresent(value -> System.out.println("Lambda方式Optional值: " + value));
    }
}
