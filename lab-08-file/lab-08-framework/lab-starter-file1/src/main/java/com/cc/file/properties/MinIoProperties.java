package com.cc.file.properties;

import lombok.Data;

@Data
public class MinIoProperties {

    /**
     * 连接端点
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 默认桶名称
     */
    private String bucketName;

}
