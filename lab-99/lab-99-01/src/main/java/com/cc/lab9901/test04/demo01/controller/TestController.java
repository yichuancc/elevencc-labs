package com.cc.lab9901.test04.demo01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 19:32
 */
@RestController
@RequestMapping("/test04")
public class TestController {

    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(18, 18, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/info1/{id}")
    public Map<String, Object> allInfo1(@PathVariable("id") Long id) {
        // 获取积分信息
        Map score = restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id);
        // 获取订单信息
        Map order = restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id);
        // 获取交易信息
        Map trade = restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id);
        return Map.of("score", score, "order", order, "trade", trade);
    }

    @GetMapping("/info2/{id}")
    public Map<String, Object> allInfo2(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>(16);
        // 获取积分信息
        CompletableFuture<Void> scoreTask = CompletableFuture.runAsync(() -> {
            System.out.printf("执行线程：%s%n", Thread.currentThread().getName());
            Map score = restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id);
            result.put("score", score);
        });
        // 获取订单信息
        CompletableFuture<Void> orderTask = CompletableFuture.runAsync(() -> {
            System.out.printf("执行线程: %s%n", Thread.currentThread().getName());
            Map order = restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id);
            result.put("order", order);
        });
        // 获取交易信息
        CompletableFuture<Void> tradeTask = CompletableFuture.runAsync(() -> {
            System.out.printf("执行线程: %s%n", Thread.currentThread().getName());
            Map trade = restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id);
            result.put("trade", trade);
        });
        scoreTask.join();
        orderTask.join();
        tradeTask.join();
        return result;
    }

    /**
     * allInfo2可能存在的问题
     * 1、线程安全问题：多个线程修改共享变量result，result是在主线程中创建的，并且是在多个子线程中直接修改的，这可能会导致线程安全问题（虽然这里不会）。
     * 2、返回值问题：CompletableFuture<Void>类型并不适合这里，因为我们实际上需要获取结果。应该使用CompletableFuture<Map<String, Object>>来存储和返回每个请求的结果。
     * 3、合并结果：我们应该在所有任务完成后合并结果，而不是直接修改主线程中的result对象。
     * 根据上面的问题，代码优化如下：
     *
     * @param id
     * @return
     */
    @GetMapping("/info3/{id}")
    public Map<String, Object> allInfo3(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        // 使用CompletableFuture.supplyAsync来返回结果
        CompletableFuture<Map<String, Object>> scoreFuture = CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id));
        CompletableFuture<Map<String, Object>> orderFuture = CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id));
        CompletableFuture<Map<String, Object>> tradeFuture = CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id));
        // 使用CompletableFuture.allOf等待所有任务完成
        CompletableFuture.allOf(scoreFuture, orderFuture, tradeFuture).join();
        // 合并结果
        Map<String, Object> result = new HashMap<>();
        result.put("score", scoreFuture.get());
        result.put("order", orderFuture.get());
        result.put("trade", tradeFuture.get());
        return result;
    }

    /**
     * allInfo3可能存在的问题
     * 1、如果有任何一个接口发生异常，那么将会导致该业务接口返回异常
     * 如下示例
     *
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/info4/{id}")
    public Map<String, Object> allInfo4(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        // 使用CompletableFuture.supplyAsync来返回结果
        CompletableFuture<Map<String, Object>> scoreFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(1 / 0);
                    return restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id);
                });
        CompletableFuture<Map<String, Object>> orderFuture = CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id));
        CompletableFuture<Map<String, Object>> tradeFuture = CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id));
        // 使用CompletableFuture.allOf等待所有任务完成
        CompletableFuture.allOf(scoreFuture, orderFuture, tradeFuture).join();
        // 合并结果
        Map<String, Object> result = new HashMap<>();
        result.put("score", scoreFuture.get());
        result.put("order", orderFuture.get());
        result.put("trade", tradeFuture.get());
        return result;
    }

    /**
     * 优化代码allInfo4，使其能够优雅地处理异常，并且当任何接口发生异常时不会影响到其他接口的调用结果。
     * 将代码做如下调整
     *
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/info5/{id}")
    public Map<String, Object> allInfo5(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        CompletableFuture<Map> scoreFuture = CompletableFuture
                .supplyAsync(() -> this.restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id))
                .exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));
        CompletableFuture<Map> orderFuture = CompletableFuture
                .supplyAsync(() -> this.restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id))
                .exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));
        CompletableFuture<Map> tradeFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(1 / 0);
                    return this.restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id);
                }).exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));

        // 使用CompletableFuture.allOf等待所有任务完成
        CompletableFuture.allOf(scoreFuture, orderFuture, tradeFuture).join();
        return Map.of("score", scoreFuture.get(), "order", orderFuture.get(), "trade", tradeFuture.get());
    }

    /**
     * 上面的代码中通过join操作来获取最终执行的结果，它会阻塞当前主线程（Tomcat线程）直到所有任务完成。如果有很多这样的请求同时到达，它会直接影响tomcat整体的吞吐量，我们可以通过接口异步处理的方式来进异步的优化
     * 代码调整如下
     *
     * @param id
     * @return
     */
    @GetMapping("/info6/{id}")
    public Callable<Map> allInfo6(@PathVariable("id") Long id) {
        System.out.printf("请求开始: %d%n", System.currentTimeMillis());
        CompletableFuture<Map> scoreFuture = CompletableFuture
                .supplyAsync(() -> this.restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id))
                .exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));
        CompletableFuture<Map> orderFuture = CompletableFuture
                .supplyAsync(() -> this.restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id))
                .exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));
        CompletableFuture<Map> tradeFuture = CompletableFuture
                .supplyAsync(() -> {
                    return this.restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id);
                }).exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));

        Callable<Map> cb = () -> {
            CompletableFuture.allOf(scoreFuture, orderFuture, tradeFuture).join();
            return Map.of("score", scoreFuture.get(), "order", orderFuture.get(), "trade", tradeFuture.get());
        };
        System.out.printf("请求结束: %d%n", System.currentTimeMillis());
        return cb;
    }

    /**
     * 在上面的代码中CompletableFuture#supplyAsync方法调用默认情况下使用的是ForkJoinPool.commonPool()。
     * 在实际的生产环境我们应该指定自己的线程池。自定义线程池更好地控制并发级别、线程数、队列深度等参数，以确保系统资源的有效利用和避免资源耗尽。
     * 使用方法如下：
     *
     * @param id
     * @return
     */
    @GetMapping("/info7/{id}")
    public Callable<Map> allInfo7(@PathVariable("id") Long id) {
        System.out.printf("请求开始: %d%n", System.currentTimeMillis());
        CompletableFuture<Map> scoreFuture = CompletableFuture
                .supplyAsync(() -> this.restTemplate.getForObject("http://localhost:9901/api/scores/{id}", Map.class, id), POOL)
                .exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));
        CompletableFuture<Map> orderFuture = CompletableFuture
                .supplyAsync(() -> this.restTemplate.getForObject("http://localhost:9901/api/orders/{id}", Map.class, id), POOL)
                .exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));
        CompletableFuture<Map> tradeFuture = CompletableFuture
                .supplyAsync(() -> {
                    return this.restTemplate.getForObject("http://localhost:9901/api/trades/{id}", Map.class, id);
                }, POOL).exceptionally(ex -> Map.of("data", String.format("接口发生异常: %s", ex.getMessage())));

        Callable<Map> cb = () -> {
            CompletableFuture.allOf(scoreFuture, orderFuture, tradeFuture).join();
            return Map.of("score", scoreFuture.get(), "order", orderFuture.get(), "trade", tradeFuture.get());
        };
        System.out.printf("请求结束: %d%n", System.currentTimeMillis());
        return cb;
    }


}
