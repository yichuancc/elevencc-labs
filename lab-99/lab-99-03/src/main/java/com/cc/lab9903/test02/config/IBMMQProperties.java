package com.cc.lab9903.test02.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cc
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
public class IBMMQProperties {
    /**
     * 地址信息
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 队列管理器名称
     */
    private String queueManager;

    /**
     * 通道名称
     */
    private String channel;

    /**
     * 连接用户名
     */
    private String username;

    /**
     * 连接密码
     */
    private String password;

    /**
     * 连接超时
     */
    private Long receiveTimeout;

}
