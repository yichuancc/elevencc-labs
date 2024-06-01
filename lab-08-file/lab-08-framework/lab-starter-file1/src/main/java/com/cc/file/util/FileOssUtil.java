package com.cc.file.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.MD5;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileOssUtil {

    /**
     * 获取文件md5
     *
     * @param stream
     * @return
     */
    public static String getFileMd5(InputStream stream) {
        return MD5.create().digestHex(stream);
    }

    /**
     * 获取文件md5
     *
     * @param multipartFile
     * @return
     */
    public static String getFileMd5(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return getFileMd5(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件拓展名
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        return FileNameUtil.getSuffix(fileName);
    }

    /**
     * 拼接文件名
     *
     * @param originalName
     * @return
     */
    public static String toFileName(String originalName, String fileSuffix) {
        if (ObjectUtils.isEmpty(fileSuffix)) {
            return originalName;
        } else {
            return originalName.concat(".").concat(fileSuffix);
        }
    }

    /**
     * 增加文件路径
     */
    public static String toPathDate(String fileName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 组成文件路径
        return formatter.format(new Date()).concat("/").concat(fileName);
    }


    /**
     * 获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        return FileNameUtil.getName(filePath);
    }


    /**
     * 下载文件
     *
     * @param response
     * @param inputStream
     * @param fileName
     */
    public static void download(HttpServletResponse response, InputStream inputStream, String fileName) {
        BufferedInputStream bis = null;
        try {
            if (inputStream != null) {
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                fileName = URLUtil.encode(fileName, StandardCharsets.UTF_8);
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                byte[] buffer = new byte[1024];
                bis = new BufferedInputStream(inputStream);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                os.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException("读取文件异常");
        } finally {
            IoUtil.close(bis);
        }
    }
}