package com.cc.lab9901.test03.demo01;

import java.util.Arrays;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-24 21:27
 */
public class ListForEach {
    public static void main(String[] args) {
        // 集合遍历
        traditionalForEach();
        lambdaForEach();
    }

    public static void traditionalForEach() {
        // 创建一个包含三个元素的列表
        List<String> list = Arrays.asList("a", "b", "c");
        // 使用传统的for-each循环遍历列表并打印每个元素
        for (String item : list) {
            System.out.println("传统方式遍历元素: " + item);
        }
    }

    public static void lambdaForEach() {
        // 创建一个包含三个元素的列表
        List<String> list = Arrays.asList("a", "b", "c");
        // 使用Lambda表达式遍历列表并打印每个元素
        list.forEach(item -> System.out.println("Lambda方式遍历元素: " + item));
    }
}
