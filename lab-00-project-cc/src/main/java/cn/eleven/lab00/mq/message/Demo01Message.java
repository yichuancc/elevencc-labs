package cn.eleven.lab00.mq.message;

import lombok.Data;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-25 15:58
 */
@Data
public class Demo01Message {
    public static final String TOPIC = "DEMO_01";

    /**
     * 编号
     */
    private Integer id;
}
