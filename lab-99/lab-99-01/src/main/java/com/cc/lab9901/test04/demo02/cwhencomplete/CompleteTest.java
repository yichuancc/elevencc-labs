package com.cc.lab9901.test04.demo02.cwhencomplete;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 20:56
 */
@Slf4j
public class CompleteTest {
    public static void main(String[] args) {
        // whenComplete(BiConsumer<? super T,? super Throwable> action)
        // 接受一个 Consumer 参数，该参数接受计算的结果（如果成功）或异常（如果发生异常）并执行相应的操作
        // 该方法是同步执行，回调函数是在触发它的 CompletableFuture 所在的线程中执行，且它会阻塞当前线程。
        // 比如在 main 线程去调用它的，所以执行他的线程就是 main 线程，它会阻塞 mian 线程执行。参考CompleteTest02
        CompletableFuture<String> completableFuture1 = whenCompleteTest1();
        System.out.println(completableFuture1.join());

        // 异步执行，回调函数会在默认的 ForkJoinPool 的线程中执行，但是它不会阻塞当前线程。
        // 将上面例子的 CompleteTest02类的whenComplete() 改成 whenCompleteAsync()，查看执行结果。参考CompleteTest03
        CompletableFuture<String> completableFuture2 = whenCompleteTest2();
        System.out.println(completableFuture2.join());

        //与前一个方法相似，只不过我们可以执行执行 Action 执行的线程池
        CompletableFuture<String> completableFuture3 = whenCompleteTest2();
        System.out.println(completableFuture3.join());

        //exceptionally() 用于处理异步操作中的异常情况，当异步操作发生异常时，该回调函数将会被执行
        // 可以在该回调函数中处理异常情况。exceptionally() 返回一个新的 CompletableFuture 对象，
        // 其中包含了异常处理的结果或者异常对象。
        exceptionallyTest();
    }

    public static CompletableFuture<String> whenCompleteTest1() {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            log.info("[completableFuture-1] - www.skjava.com 网站就是牛..");
            //System.out.println(1/0);
            return "[completableFuture-1] - 死磕 Java 新特性";
        }).whenComplete((res, ex) -> {
            if (ex == null) {
                System.out.println("结果是:" + res);
            } else {
                System.out.println("发生了异常，异常信息是:" + ex.getMessage());
            }
        });
        return completableFuture1;
    }

    public static CompletableFuture<String> whenCompleteTest2() {
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            log.info("[completableFuture-1] - www.skjava.com 网站就是牛..");
            //System.out.println(1/0);
            return "[completableFuture-1] - 死磕 Java 新特性";
        }).whenCompleteAsync((res, ex) -> {
            if (ex == null) {
                System.out.println("结果是:" + res);
            } else {
                System.out.println("发生了异常，异常信息是:" + ex.getMessage());
            }
        });
        return completableFuture2;
    }

    public static CompletableFuture<String> whenCompleteTest3() {
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            log.info("[completableFuture-3] - www.skjava.com 网站就是牛..");
            return "[completableFuture-2] - 死磕 Java 新特性";
        }).whenCompleteAsync((res, ex) -> {
            if (ex == null) {
                log.info("结果是:{}", res);
            } else {
                log.warn("发生了异常，异常信息是:{}", ex.getMessage());
            }
        }, Executors.newFixedThreadPool(4));
        return completableFuture3;
    }

    public static void exceptionallyTest() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            int i = 10 / 0;
            return "死磕 Java 并发就是牛";
        });

        CompletableFuture<String> resultFuture = future.exceptionally((ex) -> {
            log.info("发生了异常:{}", ex.getMessage());
            return "死磕 Netty 就是牛";
        });

        try {
            //由于 future 抛了异常，所以调用 future.join() 会报错， try...catch 处理下 。
            System.out.println(future.join());
        } catch (Exception ex) {
            log.error("异常:{}", ex.getMessage());
        }
        // 可参考TestController 直接在future后面加异常，此处不用再单独获取结果
        System.out.println(resultFuture.join());
    }

}
