package cn.eleven.lab00.mq.comsumer;

import cn.eleven.lab00.mq.message.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-25 16:06
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Demo01Message.TOPIC, consumerGroup = "demo01-consumer-group-" + Demo01Message.TOPIC)
public class Demo01Consumer implements RocketMQListener<Demo01Message> {
    @Override
    public void onMessage(Demo01Message demo01Message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}] ", Thread.currentThread().getId(), demo01Message);
    }
}
