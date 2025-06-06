package com.cc;

import com.cc.demo02.Lab09Demo02Application;
import com.cc.demo02.service.SeparateChatAssistant;
import com.cc.demo02.service.impl.MongoChatMemoryStore;
import dev.langchain4j.data.message.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Lab09Demo02Application.class)
public class MongoCrudTest02 {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    /**
     * 插入文档
     */
    @Test
    public void testInsert2() {
        String answer1 = separateChatAssistant.chat(1,"我是环环");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer2);
        String answer3 = separateChatAssistant.chat(2,"我是谁");
        System.out.println(answer3);
    }

    @Test
    public void testGetMessages(){
        List<ChatMessage> messages = mongoChatMemoryStore.getMessages(1);
        System.out.println(messages);
    }

}
