package com.cc.lab08fileweb1.file.builder;

import cn.hutool.core.util.ObjUtil;
import com.cc.file.properties.FileServerProperties;
import com.cc.file.service.FileService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;


@Component
public class FileServiceBuilder {
    @Resource
    private FileServerProperties fileServerProperties;
    @Resource
    private Map<String, FileService> fileServiceMap;


    public FileService getFileService() {
        //一个实现直接返回
        if (fileServiceMap.size() == 1) {
            Set<String> keySet = fileServiceMap.keySet();
            return fileServiceMap.get(keySet.iterator().next());
        }

        //默认实现
        String defaultType = fileServerProperties.getDefaultType();
        if (ObjUtil.isNotEmpty(defaultType)) {
            return getFileService(defaultType);
        }
        return null;
    }

    public FileService getFileService(String type) {
        return fileServiceMap.get(type + FileServerProperties.getImpl());
    }

    public FileService getDiskFileService() {
        return fileServiceMap.get(FileServerProperties.TYPE_DISK + FileServerProperties.getImpl());
    }

}
