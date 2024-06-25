package com.cc.lab9901.test03.demo01;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-24 21:50
 */
public class ListGroup {

    public static void main(String[] args) {
        //分组操作
        traditionalGroup();
        lambdaGroup();
    }

    public static void traditionalGroup() {
        // 创建一个包含字符串的列表
        List<String> list = Arrays.asList("a", "bb", "ccc", "dd", "eee");
        // 创建一个Map来存储分组结果
        Map<Integer, List<String>> groupedByLength = new HashMap<>();
        // 使用传统方式进行分组
        for (String item : list) {
            int length = item.length();
            if (!groupedByLength.containsKey(length)) {
                groupedByLength.put(length, new ArrayList<>());
            }
            groupedByLength.get(length).add(item);
        }
        // 打印分组结果
        System.out.println("传统方式分组结果: " + groupedByLength);
    }

    public static void lambdaGroup() {
        // 创建一个包含字符串的列表
        List<String> list = Arrays.asList("a", "bb", "ccc", "dd", "eee");
        // 使用Lambda表达式进行分组并收集结果
        Map<Integer, List<String>> groupedByLength = list.stream()
                .collect(Collectors.groupingBy(String::length));
        // 打印分组结果
        System.out.println("Lambda方式分组结果: " + groupedByLength);
    }

}
