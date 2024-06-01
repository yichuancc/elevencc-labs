package com.cc.lab08fileweb1.file.controller;


import cn.hutool.core.io.file.FileNameUtil;
import com.cc.file.service.FileService;
import com.cc.file.util.FileOssUtil;
import com.cc.lab08fileweb1.file.builder.FileServiceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author xnew
 * @description
 * @Date 2024/04/07
 */
@Slf4j
@RestController
@RequestMapping("/sysFileInfo")
public class SysFileInfoController {
    @Resource
    private FileServiceBuilder fileServiceBuilder;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        FileService fileService = fileServiceBuilder.getFileService();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 组成文件路径
        String filePath = formatter.format(new Date()).concat("/").concat(Objects.requireNonNull(file.getOriginalFilename()));
        return fileService.uploadFile(file, filePath);
    }

    @GetMapping("/download/filePath")
    public void downloadFilePath(HttpServletResponse response, @RequestParam(name = "filePath") String filePath) {
        try {
            FileService fileService = fileServiceBuilder.getFileService();
            InputStream inputStream = fileService.downloadFile(filePath);
            FileOssUtil.download(response, inputStream, FileNameUtil.getName(filePath));
        } catch (Exception e) {
            log.error("读取文件异常", e);
        }
    }
}
