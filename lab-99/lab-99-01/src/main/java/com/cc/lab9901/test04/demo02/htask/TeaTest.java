package com.cc.lab9901.test04.demo02.htask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-07-01 22:38
 */
@Slf4j
public class TeaTest {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("拿开水壶");
            return "开水壶";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("拿水壶");
            return "水壶";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            log.info("拿茶杯");
            return "茶杯";
        });

        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
            log.info("拿茶叶");
            return "西湖龙井";
        });

        CompletableFuture<String> future11 = future1.thenApply((result) -> {
            log.info("拿到" + result + ",开始洗" + result);
            return "干净的开水壶";
        });

        CompletableFuture<String> future12 = future11.thenApply((result) -> {
            log.info("拿到" + result + ",开始烧开水");
            return "烧开水了";
        });

        CompletableFuture<String> future21 = future2.thenApply((result) -> {
            log.info("拿到" + result + ",开始洗" + result);
            return "干净的水壶";
        });

        CompletableFuture<String> future31 = future3.thenApply((result) -> {
            log.info("拿到" + result + ",开始洗" + result);
            return "干净的茶杯";
        });


        CompletableFuture<Void> future5 = CompletableFuture.allOf(future4, future12, future21, future31);
        future5.thenRun(() -> {
            log.info("泡好了茶，还是喝美味的西湖龙井茶");
        });

        TimeUnit.SECONDS.sleep(5);
    }
}
