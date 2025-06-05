package com.cc;

import com.cc.demo02.Lab09Demo02Application;
import com.cc.demo02.service.Assistant;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Lab09Demo02Application.class)
public class LLMTest03 {
    /**
     * 整合SpringBoot
     */
    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testChat() {
        //创建AIService
        Assistant assistant = AiServices.create(Assistant.class, openAiChatModel);
        //调用service的接口
        String answer = assistant.chat("你好");
        System.out.println(answer);
    }
}
