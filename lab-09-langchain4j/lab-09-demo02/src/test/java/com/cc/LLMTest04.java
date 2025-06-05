package com.cc;

import com.cc.demo02.Lab09Demo02Application;
import com.cc.demo02.service.Assistant01;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Lab09Demo02Application.class)
public class LLMTest04 {
    /**
     * 整合SpringBoot
     */
    @Autowired
    private Assistant01 assistant01;

    @Test
    public void testAssistant() {
        String answer = assistant01.chat("Hello");
        System.out.println(answer);
    }
}
