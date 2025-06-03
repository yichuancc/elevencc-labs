package com.cc;

import com.cc.demo01.Lab09Demo02Application;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2025-06-03 14:32
 */
@SpringBootTest(classes = Lab09Demo02Application.class)
public class LLMTest02 {
    /**
     * 整合SpringBoot
     */
    @Autowired
    private OpenAiChatModel openAiChatModel;
    @Test
    public void testSpringBoot() {
        //向模型提问
        String answer = openAiChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }
}
