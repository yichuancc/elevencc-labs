package com.cc.lab9901.test04.demo02.gapplyto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 22:14
 */
@Slf4j
public class ApplyToEitherTest {
    public static void main(String[] args) {
        // applyToEither() 用于处理两个不同的 CompletableFuture 异异步操作中的任何一个完成后，
        // 将其结果应用于一个函数，并返回一个新的 CompletableFuture 表示该函数的输出结果。
        // 该方法允许在两个异步操作中的任何一个完成时执行操作，而不需要等待它们都完成。
        applyToEitherTest();

        // acceptEither() 与 applyToEither() 一样，也是等待两个 CompletableFuture 中的任意一个执行完成后执行操作，但是它不返回结果。
        acceptEitherTest();

        // runAfterEither()用于在两个不同的 CompletableFuture 异步操作中的任何一个完成后执行操作，而不依赖操作的结果。
        // 这个方法通常用于在两个异步操作中的任何一个成功完成时触发清理操作或执行某些操作，而不需要返回值。
        runAfterEitherTest();

        // runAfterBoth() 用于在两个不同的 CompletableFuture 异步操作都完成后执行操作，而不依赖操作的结果。
        // 这个方法通常用于在两个异步操作都完成时触发某些操作或清理工作，而不需要返回值。
        runAfterBothTest();

        // anyOf() 是用于处理多个 CompletableFuture 对象的静态方法，它允许我们等待多个异步操作中的任何一个完成，并执行相应的操作。
        // 它类似于多个异步操作的并发执行，只要有一个操作完成，它就会返回一个新的 CompletableFuture 对象，表示第一个完成的操作。
        // anyOf() 是一个可变参数，我们可以传入任意数量的 CompletableFuture 对象。
        anyOfTest();

        //anyOf() 是任一一个异步任务完成就会触发，而 allOf() 则需要所有异步都要完成
        allOfTest();
    }

    public static void applyToEitherTest() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("死磕 Netty");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("死磕 Java 新特性");

        CompletableFuture<String> eitherFuture = future1.applyToEither(future2, res -> {
            System.out.println("接受的结果是:" + res);
            return "eitherFuture 接受的结果是:" + res;
        });
        System.out.println(eitherFuture.join());
    }

    public static void acceptEitherTest() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("死磕 Netty");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("死磕 Java 新特性");
        CompletableFuture<Void> eitherFuture = future1.acceptEither(future2, res -> {
            System.out.println("接受的结果是:" + res);
        });
        eitherFuture.join();
    }

    public static void runAfterEitherTest() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("死磕 Netty");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("死磕 Java 并发");
        future1.runAfterEither(future2, () -> {
            System.out.println("已经有一个任务完成了...");
        });
    }

    public static void runAfterBothTest() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("死磕 Netty");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("死磕 Java 并发");
        future1.runAfterBoth(future2, () -> {
            System.out.println("future1 和 future2 两个异步任务都完成了...");
        });
    }

    public static void anyOfTest() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            log.info("死磕 Netty 执行完成...");
            return "死磕 Netty";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            log.info("死磕 Java 并发 执行完成...");
            return "死磕 Java 并发";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            sleep(3);
            log.info("死磕 Redis 执行完成...");
            return "死磕 Redis";
        });

        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
            sleep(4);
            log.info("死磕 Java 新特性 执行完成...");
            return "死磕 Java 新特性";
        });

        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            log.info("死磕 Spring 执行完成...");
            return "死磕 Spring";
        });

        //anyOf() 比较有用，当我们需要并行执行多个异步操作，并在其中任何一个完成时执行操作时，就可以使用它。
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3, future4, future5);
        anyOfFuture.thenAccept(result -> {
            log.info("接收到的结果为:" + result);
        });
        sleep(10);
    }

    public static void allOfTest() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            log.info("死磕 Netty 执行完成...");
            return "死磕 Netty";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            log.info("死磕 Java 并发 执行完成...");
            return "死磕 Java 并发";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            sleep(3);
            log.info("死磕 Redis 执行完成...");
            return "死磕 Redis";
        });

        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
            sleep(4);
            log.info("死磕 Java 新特性 执行完成...");
            return "死磕 Java 新特性";
        });

        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            log.info("死磕 Spring 执行完成...");
            return "死磕 Spring";
        });

        //anyOf() 比较有用，当我们需要并行执行多个异步操作，并在其中任何一个完成时执行操作时，就可以使用它。
        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2, future3, future4, future5);
        future.thenAccept(result -> {
            log.info("接收到的结果为:" + result);
        });
        sleep(10);
    }

    public static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
