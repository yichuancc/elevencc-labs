package com.cc.lab9901.test03.demo01;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 16:36
 */
public class ListStream {
    public static void main(String[] args) {
        // Stream
        traditionalStream();
        lambdaStream();
    }

    public static void traditionalStream() {
        // 创建一个包含字符串的列表
        List<String> list = Arrays.asList("a", "bb", "ccc", "dd", "eee");
        // 创建一个新的列表来存储过滤和映射后的结果
        List<String> result = new ArrayList<>();
        // 使用传统方式进行过滤和映射
        for (String s : list) {
            if (s.length() > 1) {
                result.add(s.toUpperCase());
            }
        }
        // 使用传统方式进行排序
        Collections.sort(result, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        // 打印最终结果
        System.out.println("传统方式Stream操作结果: " + result);
    }

    public static void lambdaStream() {
        // 创建一个包含字符串的列表
        List<String> list = Arrays.asList("a", "bb", "ccc", "dd", "eee");
        // 使用Lambda表达式进行过滤、映射、排序并收集结果
        List<String> result = list.stream()
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        // 打印最终结果
        System.out.println("Lambda方式Stream操作结果: " + result);
    }
}
