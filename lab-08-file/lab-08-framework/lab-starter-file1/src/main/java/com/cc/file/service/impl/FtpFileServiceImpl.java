package com.cc.file.service.impl;


import com.cc.file.properties.FileServerProperties;
import com.cc.file.service.FileService;
import com.cc.file.template.FtpTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 *
 */
@Slf4j
@Service(FileServerProperties.TYPE_FTP + FileServerProperties.IMPL)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX + "." + FileServerProperties.TYPE_FTP, name = FileServerProperties.ENABLED, havingValue = FileServerProperties.HAVING_VALUE)
public class FtpFileServiceImpl implements FileService {

    @Resource
    private FtpTemplate ftpTemplate;

    @Resource
    private FileServerProperties fileServerProperties;

    @Override
    public String uploadFile(MultipartFile multipartFile, String filePath) {
        return ftpTemplate.uploadFile(multipartFile, filePath);
    }

    @Override
    public void deleteFile(String path) {
        ftpTemplate.deleteFile(path);
    }

    @Override
    public InputStream downloadFile(String path) {
        return ftpTemplate.downloadFile(path);
    }


    @Override
    public String getStoreType() {
        return FileServerProperties.TYPE_FTP;
    }

    @Override
    public String getBasePath() {
        return fileServerProperties.getFtp().getBasePath();
    }
}
