package com.cc.lab9901.test04.demo02.bgetjoin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 20:49
 */
public class BgetJoinTest {
    public static void main(String[] args) {
        // get() 会抛出 InterruptedException 和 ExecutionException 这两个受检查异常，必须显式地在代码中处理这些异常或将它们抛出。
        // join() 不会抛出受检查异常，所以在使用过程中代码会显得更加简洁，但是如果任务执行中发生异常，它会包装在 CompletionException 中，需要在后续代码中处理
        completedFutureTest();
    }

    public static void completedFutureTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("死磕 Java 就是牛");
        System.out.println(completableFuture.join());
        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            // 捕获异常并处理
            // 或者直接抛出
        }
    }
}
