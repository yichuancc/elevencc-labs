package com.cc;

import com.cc.demo02.Lab09Demo02Application;
import com.cc.demo02.service.MemoryChatAssistant;
import com.cc.demo02.service.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Lab09Demo02Application.class)
public class PromptTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testSystemMessage() {
        String answer = separateChatAssistant.chatSys(3, "今天几号");
        System.out.println(answer);
    }

    @Test
    public void testSystemMessageText() {
        String answer = separateChatAssistant.chatSysText(4, "今天几号");
        System.out.println(answer);
    }

    @Test
    public void testUserMessage() {
        String answer = memoryChatAssistant.chatUser("我是环环");
        System.out.println(answer);
    }

    @Test
    public void testUserV() {
        String answer = memoryChatAssistant.chatV("我是环环");
        System.out.println(answer);
    }

    @Test
    public void testUserV2() {
        String answer = separateChatAssistant.chatV2(1, "我是环环");
        System.out.println(answer);
    }

    @Test
    public void testUserInfo() {
        String answer = separateChatAssistant.chatV3(11, "我是谁，我多大了", "cc", 18);
        System.out.println(answer);
    }

}
