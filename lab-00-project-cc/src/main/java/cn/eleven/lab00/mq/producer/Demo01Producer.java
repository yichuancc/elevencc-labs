package cn.eleven.lab00.mq.producer;

import cn.eleven.lab00.mq.message.Demo01Message;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-25 16:00
 */
@Component
public class Demo01Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        Demo01Message message = new Demo01Message();
        message.setId(id);
        return rocketMQTemplate.syncSend(Demo01Message.TOPIC, message);
    }

    public void asyncSend(Integer id, SendCallback callback) {
        Demo01Message message = new Demo01Message();
        message.setId(id);
        rocketMQTemplate.asyncSend(Demo01Message.TOPIC, message, callback);
    }

    public void onewaySend(Integer id) {
        Demo01Message message = new Demo01Message();
        message.setId(id);
        rocketMQTemplate.sendOneWay(Demo01Message.TOPIC,message);
    }
}
