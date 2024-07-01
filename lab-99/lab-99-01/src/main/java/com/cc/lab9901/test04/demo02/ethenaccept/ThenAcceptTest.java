package com.cc.lab9901.test04.demo02.ethenaccept;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 21:52
 */
@Slf4j
public class ThenAcceptTest {
    public static void main(String[] args) throws InterruptedException {
        // thenAccept()：用于处理异步操作的结果，但不返回任何结果。
        thenAcceptTest();
        // thenAcceptBoth()：用于处理两个不同的 CompletableFuture 异步操作的结果，并执行操作，但不返回新的结果。
        thenAcceptBothTest();
        //thenRun() 通常用于执行其他作用的操作、清理工作、或在异步操作完成后触发其他操作。
        thenRunTest();
    }

    public static void thenAcceptTest() throws InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "死磕 Java 新特性";
        });

        CompletableFuture<Void> future = completableFuture.thenAccept(s -> {
            System.out.println("CompletableFuture 计算结果是:" + s);
        });

        TimeUnit.SECONDS.sleep(5);
    }

    public static void thenAcceptBothTest() throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("死磕 Netty");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("死磕 Java 新特性");

        //other：为另外一个 CompletableFuture，它包含了另一个异步操作的结果。
        //action：类型为 BiConsumer，它接受两个参数，分别表示第一个 CompletableFuture 的结果和第二个 CompletableFuture 的结果
        future1.thenAcceptBoth(future2, (result1, result2) -> {
            System.out.println("future1 的结果是：" + result1);
            System.out.println("future2 的结果是：" + result2);
        });

        TimeUnit.SECONDS.sleep(5);
    }

    public static void thenRunTest() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("死磕 Netty");
        future.thenRun(() -> {
            System.out.println("CompletableFuture 计算执行完成，开始执行后续操作...");
        });

        TimeUnit.SECONDS.sleep(5);
    }
}
