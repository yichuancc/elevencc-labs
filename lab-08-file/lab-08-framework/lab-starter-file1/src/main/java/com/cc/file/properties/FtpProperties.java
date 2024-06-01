package com.cc.file.properties;

import lombok.Data;

@Data
public class FtpProperties {

    /**
     * ftp服务器的地址
     */
    private String host;
    /**
     * ftp服务器的端口号（连接端口号）
     */
    private Integer port;
    /**
     * ftp的用户名
     */
    private String username;
    /**
     * ftp的密码
     */
    private String password;
    /**
     * ftp上传的根目录
     */
    private String basePath;
    /**
     * ftp连接超时时间，单位毫秒
     */
    private Integer timeout;
    /**
     * 回显地址
     */
    private String httpPath;
}
