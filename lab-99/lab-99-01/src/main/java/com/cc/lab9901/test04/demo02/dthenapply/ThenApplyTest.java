package com.cc.lab9901.test04.demo02.dthenapply;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 21:37
 */
@Slf4j
public class ThenApplyTest {
    public static void main(String[] args) {
        // thenApply()和 thenApplyAsync()：用于将一个 CompletableFuture 的结果应用于一个函数，并返回一个新的 CompletableFuture，表示转换后的结果。
        thenApplyTest();
        thenApplyAsyncTest();
        // thenCompose() 和 thenComposeAsync() ：它用于将一个 CompletableFuture 的结果应用于一个函数，该函数返回一个新的 CompletableFuture。
        thenComposeTest();
        thenComposeAsyncTest();

        // thenCompose() 与 thenApply() 两者的返回值虽然都是新的 CompletableFuture，
        // 但是 thenApply() 由于它的函数的返回值仅仅只是结果，所以它通常用于对异步操作的结果进行简单的转换，
        // 而 thenCompose() 则允许我们链式地组合多个异步操作。虽然两者都有可能实现相同的效果（比如上面例子），
        // 但是他们的使用场景和意义还是有区别的。
    }

    public static void thenApplyTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("执行第一步...");
            return "死磕 Java";
        }).thenApply(s -> {
            log.info("执行第二步,第一步返回结果:{}", s);
            return s + " 就是牛..";
        });
        log.info("结果为:{}", completableFuture.join());
    }

    public static void thenApplyAsyncTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("执行第一步...");
            return "死磕 Java";
        }).thenApplyAsync(s -> {
            log.info("执行第二步,第一步返回结果:{}", s);
            return s + " 就是牛..";
        });
        log.info("结果为:{}", completableFuture.join());
    }

    public static void thenComposeTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("执行第一步...");
            return "死磕 Java";
        }).thenCompose((s) -> {
            log.info("执行第二步,第一步返回结果:{}",s);
            // 注意这里跟 thenApply() 的差异
            return CompletableFuture.supplyAsync(() -> s + " 就是牛..");
        });

        log.info("结果为:{}",completableFuture.join());
    }

    public static void thenComposeAsyncTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("执行第一步...");
            return "死磕 Java";
        }).thenComposeAsync((s) -> {
            log.info("执行第二步,第一步返回结果:{}",s);
            // 注意这里跟 thenApply() 的差异
            return CompletableFuture.supplyAsync(() -> s + " 就是牛..");
        });

        log.info("结果为:{}",completableFuture.join());
    }
}
