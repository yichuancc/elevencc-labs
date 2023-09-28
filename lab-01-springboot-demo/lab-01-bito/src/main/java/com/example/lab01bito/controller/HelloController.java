package com.example.lab01bito.controller;

import com.example.lab01bito.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-06-24 13:25
 */
@RestController
public class HelloController {

    @Resource
    private HelloService helloService;
    @GetMapping("/hello")
    public String hello() {
        helloService.hello();
        return "Hello, World!";
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println("Hello, World!");
    }
}
