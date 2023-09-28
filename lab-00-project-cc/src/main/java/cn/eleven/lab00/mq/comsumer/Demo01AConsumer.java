package cn.eleven.lab00.mq.comsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-25 16:11
 */
@Slf4j
//@Component
//@RocketMQMessageListener(
//        topic = Demo01Message.TOPIC,
//        consumerGroup = "demo01-A-consumer-group-" + Demo01Message.TOPIC
//)
public class Demo01AConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
