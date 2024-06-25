package com.cc.lab9901.test03.demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-24 21:40
 */
public class ListMap {
    public static void main(String[] args) {
        // 映射操作
        traditionalMap();
        lambdaMap();
    }

    public static void traditionalMap() {
        // 创建一个包含字符串数字的列表
        List<String> list = Arrays.asList("1", "2", "3");
        // 创建一个新的列表来存储映射后的结果
        List<Integer> mappedList = new ArrayList<>();
        // 使用传统方式进行映射
        for (String item : list) {
            mappedList.add(Integer.parseInt(item));
        }
        // 打印映射后的列表
        System.out.println("传统方式映射结果: " + mappedList);
    }

    public static void lambdaMap() {
        // 创建一个包含字符串数字的列表
        List<String> list = Arrays.asList("1", "2", "3");
        // 使用Lambda表达式进行映射并收集结果
        List<Integer> mappedList = list.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        // 打印映射后的列表
        System.out.println("Lambda方式映射结果: " + mappedList);
    }
}
