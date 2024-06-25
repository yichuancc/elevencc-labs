package com.cc.lab9901.test03.demo01;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 16:30
 */
public class FunctionalInterface {
    public static void main(String[] args) {
        // 函数式接口
        traditionalFunctionalInterface();
        lambdaFunctionalInterface();
    }

    public static void traditionalFunctionalInterface() {
        // 使用传统方式创建线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("传统方式创建线程");
            }
        };
        new Thread(runnable).start();
    }

    public static void lambdaFunctionalInterface() {
        // 使用Lambda表达式创建线程
        Runnable runnable = () -> System.out.println("Lambda方式创建线程");
        new Thread(runnable).start();
    }
}
