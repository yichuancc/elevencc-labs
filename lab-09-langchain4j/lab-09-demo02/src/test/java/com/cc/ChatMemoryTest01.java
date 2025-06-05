package com.cc;


import com.cc.demo02.Lab09Demo02Application;
import com.cc.demo02.service.Assistant01;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = Lab09Demo02Application.class)
public class ChatMemoryTest01 {

    @Autowired
    private Assistant01 assistant01;

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testChatMemory() {
        String answer1 = assistant01.chat("我是环环");
        System.out.println(answer1);
        String answer2 = assistant01.chat("我是谁");
        System.out.println(answer2);
    }

    @Test
    public void testChatMemory2() {
        //第一轮对话
        UserMessage userMessage1 = UserMessage.userMessage("我是环环");
        ChatResponse chatResponse1 = openAiChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage1.text());
        //第二轮对话
        UserMessage userMessage2 = UserMessage.userMessage("你知道我是谁吗");
        ChatResponse chatResponse2 = openAiChatModel.chat(Arrays.asList(userMessage1,
                aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage2.text());
    }
}
