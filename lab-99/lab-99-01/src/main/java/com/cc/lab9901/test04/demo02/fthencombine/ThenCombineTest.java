package com.cc.lab9901.test04.demo02.fthencombine;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 22:05
 */
@Slf4j
public class ThenCombineTest {
    public static void main(String[] args) {
        // thenCombine() 用于将两个不同的 CompletableFuture 异步操作的结果合并为一个新的结果，并执行操作。
        // 该方法允许我们在两个异步操作都完成后执行一个操作，它接受两个结果作为参数，并返回一个新的结果。
        thenCombineTest();
    }

    public static void thenCombineTest() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("死磕 Netty");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("死磕 Java 新特性");

        //other：表示另外一个 CompletableFuture，它包含了该 CompletableFuture 的计算结果
        //action：类型是 BiFunction，它接受两个参数，分别是第一个 CompletableFuture 的计算结果和第二个 CompletableFuture 的计算结果。
        CompletableFuture<String> combineFuture = future1.thenCombine(future2, (result1, result2) -> {
            System.out.println("future1 的结果是：" + result1);
            System.out.println("future2 的结果是：" + result2);
            return result1 + "和" + result1 + " 就是牛...";
        });

        System.out.println(combineFuture.join());
    }

}
