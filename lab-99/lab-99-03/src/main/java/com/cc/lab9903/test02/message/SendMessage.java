package com.cc.lab9903.test02.message;

import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * ibm mq 消息发送者
 * @author cc
 */
@Component
public class SendMessage {
    @Resource
    JmsOperations jmsOperations;

    /**
     * @PostConstruct 在服务器加载的时候运行，并且只会被服务器执行一次, @PreDestroy在destroy()方法执行执行之后执行
     */
    @PostConstruct
    public void send() {
        jmsOperations.convertAndSend("MDM_Practitioner_Create_Ylzkpt", "my message...");
        System.out.println("开始发送消息");
    }
}
