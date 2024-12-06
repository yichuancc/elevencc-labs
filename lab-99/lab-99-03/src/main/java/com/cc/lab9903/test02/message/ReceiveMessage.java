package com.cc.lab9903.test02.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * ibm mq 消息接受者
 *
 * @author cc
 */
@Component
public class ReceiveMessage extends MessageListenerAdapter {
    @Override
    @JmsListener(destination = "MDM_Practitioner_Create_Ylzkpt") //队列
    public void onMessage(Message message) {
        //必须转换如果不转换直接message.tostring消息的传输有限制。//转换成文本消息
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("MDM_Practitioner_Create_Ylzkpt传来的值为:" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
