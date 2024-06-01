package com.cc.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = FileServerProperties.PREFIX)
public class FileServerProperties {
    public static final String IMPL = "FileServiceImpl";
    public static final String PREFIX = "file-server";
    public static final String ENABLED = "enabled";
    public static final String HAVING_VALUE = "true";
    public static final String TYPE_MINIO = "minio";
    public static final String TYPE_FTP = "ftp";
    public static final String TYPE_DISK = "disk";

    /**
     * 文件对象类型
     */
    private String type;

    /**
     * 默认对象存储
     */
    private String defaultType;

    /**
     * Minio配置
     */
    MinIoProperties minio = new MinIoProperties();

    /**
     * FTP配置
     */
    FtpProperties ftp = new FtpProperties();
    /**
     * 本地磁盘
     */
    DiskProperties disk = new DiskProperties();

    /**
     * 获取实现配置后缀
     *
     * @return
     */
    public static String getImpl() {
        return IMPL;
    }
}
