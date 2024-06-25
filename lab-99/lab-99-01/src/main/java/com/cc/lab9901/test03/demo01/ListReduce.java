package com.cc.lab9901.test03.demo01;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-24 21:43
 */
public class ListReduce {
    public static void main(String[] args) {
        // 计算操作
        traditionalReduce();
        lambdaReduce();
    }

    public static void traditionalReduce() {
        // 创建一个包含数字的列表
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 使用传统方式进行求和
        int sum = 0;
        for (Integer num : list) {
            sum += num;
        }
        // 打印求和结果
        System.out.println("传统方式求和结果: " + sum);

        // 计算平均值
        double average = sum / (double) list.size();
        // 打印平均值结果
        System.out.println("传统方式求平均值结果: " + average);
    }

    public static void lambdaReduce() {
        // 创建一个包含数字的列表
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 使用Lambda表达式进行求和
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        // 打印求和结果
        System.out.println("Lambda方式求和结果: " + sum);

        // 使用Lambda表达式计算平均值
        OptionalDouble average = list.stream()
                .mapToInt(Integer::intValue)
                .average();
        // 打印平均值结果
        System.out.println("Lambda方式求平均值结果: " + average.getAsDouble());
    }
}
