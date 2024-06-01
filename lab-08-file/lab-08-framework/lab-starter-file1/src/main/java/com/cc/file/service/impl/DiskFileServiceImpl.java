package com.cc.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.cc.file.properties.FileServerProperties;
import com.cc.file.service.FileService;
import com.cc.file.util.FileOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * disk上传文件
 */
@Slf4j
@Service(FileServerProperties.TYPE_DISK + FileServerProperties.IMPL)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX + "." + FileServerProperties.TYPE_DISK, name = FileServerProperties.ENABLED, havingValue = FileServerProperties.HAVING_VALUE)
public class DiskFileServiceImpl implements FileService {

    @Resource
    private FileServerProperties fileServerProperties;


    @Override
    public String uploadFile(MultipartFile multipartFile, String filePath) {
        //获取基础路径
        String basePath = getBasePath();
        //获取扩展路径
        String extPath = StrUtil.subBefore(filePath, "/", true);
        String dirPath = basePath;
        if (StrUtil.isNotEmpty(extPath)) {
            dirPath = basePath + "/" + extPath;
        }
        File baseFile = new File(dirPath);
        if (!FileUtil.exist(baseFile)) {
            baseFile.mkdirs();
        }
        //文件全路径
        String path;
        //获取文件名称
        String fileName = FileOssUtil.getFileName(filePath);
        //文件扩展名
        try (InputStream inputStream = multipartFile.getInputStream()) {
            path = dirPath + "/" + fileName;
            FileUtil.writeFromStream(inputStream, new File(path));
            return path;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！");
        }
    }

    @Override
    public void deleteFile(String path) {
        if (!FileUtil.exist(path)) {
            return;
        }
        try {
            FileUtil.del(path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("删除文件失败！");
        }
    }

    @Override
    public InputStream downloadFile(String path) {
        File pathFile = new File(getBasePath());
        if (!FileUtil.exist(pathFile)) {
            return null;
        }
        try {
            return Files.newInputStream(Paths.get(path));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！");
        }
    }

    @Override
    public String getStoreType() {
        return FileServerProperties.TYPE_DISK;
    }

    @Override
    public String getBasePath() {
        String basePath = fileServerProperties.getDisk().getBasePath();
        if (StrUtil.isBlank(basePath)) {
            log.error("请配置文件基础路径");
        }
        return basePath;
    }

}
