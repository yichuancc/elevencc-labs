package com.example.lab01bito.controller;

import com.example.lab01bito.service.HelloService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HelloControllerTest {
    @Mock
    private HelloService helloService;
    @InjectMocks
    private HelloController helloController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHello() {
        // 设置依赖服务的行为
        // Mockito.doNothing().when(helloService).hello();
        // 执行被测试的方法
        String result = helloController.hello();
        // 验证结果是否符合预期
        Assert.assertEquals("Hello, World!", result);
        // 验证依赖服务的方法是否被调用
        // Mockito.verify(helloService).hello();
    }
}