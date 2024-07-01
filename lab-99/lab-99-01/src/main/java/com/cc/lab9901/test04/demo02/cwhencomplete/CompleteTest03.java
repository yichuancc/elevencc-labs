package com.cc.lab9901.test04.demo02.cwhencomplete;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 20:56
 */
@Slf4j
public class CompleteTest03 {

    private static CompletableFuture<String> future;

    public static void main(String[] args) {

        // whenCompleteAsync() 方法的执行线程是 ForkJoinPool.commonPool-worker-9
        // 没有阻塞 test-tread 线程的执行
        future = CompletableFuture.supplyAsync(() -> {
            log.info("CompletableFuture 主体执行");
            return "死磕 Java 新特性";
        });

        Thread thread = new Thread(() -> {
            log.info("thread 线程开始执行");
            future.whenCompleteAsync((res, ex) -> {
                log.info("whenComplete 主体开始执行");
                sleep(5);
                if (ex == null) {
                    log.info("whenComplete 执行结果:{}", res);
                } else {
                    log.info("whenComplete 执行异常:{}", ex.getMessage());
                }
                log.info("whenComplete 主体执行完毕");
            });
            log.info("thread 线程执行完成");
        });
        thread.setName("test-thread");
        thread.start();
        // 阻塞主线程
        sleep(15);
        log.info("主线程执行完毕");
    }

    public static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
