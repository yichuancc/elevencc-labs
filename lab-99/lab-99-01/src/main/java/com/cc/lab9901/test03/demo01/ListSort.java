package com.cc.lab9901.test03.demo01;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-24 21:31
 */
public class ListSort {
    public static void main(String[] args) {
        // 集合排序
        traditionalSort();
        lambdaSort();
    }

    public static void traditionalSort() {
        // 创建一个未排序的列表
        List<String> list = Arrays.asList("b", "a", "c");
        // 使用传统方式进行排序
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        // 打印排序后的列表
        System.out.println("传统方式排序结果: " + list);
    }

    public static void lambdaSort() {
        // 创建一个未排序的列表
        List<String> list = Arrays.asList("b", "a", "c");
        // 使用Lambda表达式进行排序
        list.sort((s1, s2) -> s1.compareTo(s2));
        // 打印排序后的列表
        System.out.println("Lambda方式排序结果: " + list);
    }
}
