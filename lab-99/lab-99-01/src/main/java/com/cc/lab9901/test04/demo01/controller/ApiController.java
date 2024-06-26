package com.cc.lab9901.test04.demo01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 19:36
 */
@RequestMapping("/api")
@RestController
public class ApiController {
    /**
     * 积分接口
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/scores/{id}")
    public Map<String, Object> scores(@PathVariable("id") Long id) throws Exception {
        // 模拟耗时操作
        TimeUnit.SECONDS.sleep(1);
        return Map.of("data", String.format("获取用户【%d】积分成功", id));
    }

    /**
     * 订单接口
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/orders/{id}")
    public Map<String, Object> orders(@PathVariable("id") Long id) throws Exception {
        // 模拟耗时操作
        TimeUnit.SECONDS.sleep(2);
        return Map.of("data", String.format("获取用户【%d】订单成功", id));
    }

    /**
     * 交易接口
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/trades/{id}")
    public Map<String, Object> trades(@PathVariable("id") Long id) throws Exception {
        // 模拟耗时操作
        TimeUnit.SECONDS.sleep(2);
        return Map.of("data", String.format("获取用户【%d】交易成功", id));
    }

}
