package com.cc;


import com.cc.demo02.Lab09Demo02Application;
import com.cc.demo02.service.MemoryChatAssistant;
import com.cc.demo02.service.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Lab09Demo02Application.class)
public class ChatMemoryTest02 {

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory4() {
        String answer1 = memoryChatAssistant.chat("我是环环");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat("我是谁");
        System.out.println(answer2);
    }

    @Test
    public void testChatMemory5() {
        String answer1 = separateChatAssistant.chat(1,"我是环环");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer2);
        String answer3 = separateChatAssistant.chat(2,"我是谁");
        System.out.println(answer3);
    }
}
