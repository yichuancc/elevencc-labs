package com.cc.lab9901.test04.demo02.aasync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-30 12:32
 */
@Slf4j
public class AsyncTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // runAsync()：用于异步执行没有返回值的任务。
        // runAsync(Runnable runnable) 会使用 ForkJoinPool 作为它的线程池执行异步代码。
        // runAsync(Runnable runnable, Executor executor) 则是使用指定的线程池执行异步代码。
        runAsyncTest();

        // supplyAsync()： 用于异步执行有返回值的任务。
        // supplyAsync() 也有两个重载方法，区别 runAsync() 和一样
        supplyAsyncTest();

        // completedFuture()：创建一个已完成的 CompletableFuture，它包含特定的结果。
        completedFutureTest();

        // 使用默认线程池会有一个：在主线程任务执行完以后，如果异步线程执行任务还没执行完，它会直接把异步任务线程清除掉.
        // 因为默认线程池中的都是守护线程 ForkJoinPool，当没有用户线程以后，会随着 JVM 一起清除。
        runAsyncTest2();
    }

    public static void runAsyncTest() {
        CompletableFuture.runAsync(() -> {
            log.info("死磕 Java 新特性 - 01");
        });
        CompletableFuture.runAsync(() -> {
            log.info("死磕 Java 新特性 - 02");
        }, Executors.newFixedThreadPool(10));
    }

    public static void supplyAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            log.info("死磕 Java 新特性 - 01");
            return "死磕 Java 新特性 - 01";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            log.info("死磕 Java 新特性 - 02");
            return "死磕 Java 新特性 - 02";
        }, Executors.newFixedThreadPool(10));
        log.info(completableFuture1.get());
        log.info(completableFuture2.get());
    }

    public static void completedFutureTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("死磕 Java 就是牛");
        System.out.println(completableFuture.join());
    }

    public static void runAsyncTest2() {
        CompletableFuture.runAsync(() -> {
            log.info("CompletableFuture 任务开始执行...");
            for (int i = 0; i < 100; i++) {
                log.info("CompletableFuture 任务执行中[{}]...", i);
            }
            log.info("CompletableFuture 任务执行完毕...");
        });
        log.info("主线程执行完毕...");
    }
}
