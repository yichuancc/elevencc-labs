package com.cc.liteflow.controller;

import com.cc.liteflow.excutor.FlowTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-03-25 09:56
 */
@RestController
public class FlowController {

    @Autowired
    private FlowTest flowTest;

    @GetMapping("/hello")
    public void testConfig() {
        flowTest.testConfig();
    }
}
