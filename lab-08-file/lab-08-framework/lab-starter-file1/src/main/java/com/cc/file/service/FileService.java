package com.cc.file.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param multipartFile
     * @param filePath
     * @return
     */
    String uploadFile(MultipartFile multipartFile, String filePath);

    /**
     * 删除文件
     *
     * @param path
     */
    void deleteFile(String path);

    /**
     * 下载文件
     *
     * @param path
     * @return
     */
    InputStream downloadFile(String path);

    /**
     * 获取存储类型
     *
     * @return
     */
    String getStoreType();

    /**
     * 获取基础路径
     *
     * @return
     */
    String getBasePath();
}
