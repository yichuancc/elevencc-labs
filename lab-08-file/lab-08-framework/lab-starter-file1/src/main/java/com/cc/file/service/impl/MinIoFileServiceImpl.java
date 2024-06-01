package com.cc.file.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cc.file.enums.PolicyType;
import com.cc.file.properties.FileServerProperties;
import com.cc.file.service.FileService;
import com.cc.file.template.MinIoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 */
@Slf4j
@Service(FileServerProperties.TYPE_MINIO + FileServerProperties.IMPL)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX + "." + FileServerProperties.TYPE_MINIO, name = FileServerProperties.ENABLED, havingValue = FileServerProperties.HAVING_VALUE)
public class MinIoFileServiceImpl implements FileService {

    @Resource
    private MinIoTemplate minIoTemplate;

    @Resource
    private FileServerProperties fileServerProperties;

    @Override
    public String uploadFile(MultipartFile multipartFile, String filePath) {
        //创建一个可直接通过link访问的存储桶
        minIoTemplate.createBucket(getBasePath(), PolicyType.READ);
        return minIoTemplate.uploadFile(getBasePath(), filePath, multipartFile);
    }

    @Override
    public void deleteFile(String path) {
        String bucketName = StrUtil.subBefore(path, "/", false);
        String objectName = StrUtil.subAfter(path, "/", false);
        minIoTemplate.removeFile(bucketName, objectName);
    }

    @Override
    public InputStream downloadFile(String path) {
        String bucketName = StrUtil.subBefore(path, "/", false);
        String objectName = StrUtil.subAfter(path, "/", false);
        return minIoTemplate.downloadFile(bucketName, objectName);
    }


    @Override
    public String getStoreType() {
        return FileServerProperties.TYPE_MINIO;
    }

    @Override
    public String getBasePath() {
        return fileServerProperties.getMinio().getBucketName();
    }
}
