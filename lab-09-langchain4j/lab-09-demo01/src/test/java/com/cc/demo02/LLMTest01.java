package com.cc.demo02;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2025-06-03 14:32
 */
@SpringBootTest(classes = Lab09Demo01Application.class)
public class LLMTest01 {
    @Test
    void contextLoads() {
    }

    @Test
    public void testGPTDemo() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        //向模型提问
        String answer = model.chat("你好");
        //输出结果
        System.out.println(answer);
    }
}
