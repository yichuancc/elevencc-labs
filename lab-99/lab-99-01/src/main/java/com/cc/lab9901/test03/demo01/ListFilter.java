package com.cc.lab9901.test03.demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-24 21:36
 */
public class ListFilter {
    public static void main(String[] args) {
        // 集合过滤
        traditionalFilter();
        lambdaFilter();
    }

    public static void traditionalFilter() {
        // 创建一个列表
        List<String> list = Arrays.asList("a", "b", "c", "aa");
        // 创建一个新的列表来存储过滤后的结果
        List<String> filteredList = new ArrayList<>();
        // 使用传统方式进行过滤
        for (String item : list) {
            if (item.startsWith("a")) {
                filteredList.add(item);
            }
        }
        // 打印过滤后的列表
        System.out.println("传统方式过滤结果: " + filteredList);
    }

    public static void lambdaFilter() {
        // 创建一个列表
        List<String> list = Arrays.asList("a", "b", "c", "aa");
        // 使用Lambda表达式进行过滤并收集结果
        List<String> filteredList = list.stream()
                .filter(item -> item.startsWith("a"))
                .collect(Collectors.toList());
        // 打印过滤后的列表
        System.out.println("Lambda方式过滤结果: " + filteredList);
    }
}
