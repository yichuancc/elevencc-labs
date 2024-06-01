package com.cc.file.template;

import cn.hutool.core.util.StrUtil;
import com.cc.file.properties.FileServerProperties;
import com.cc.file.util.FileOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * FTP存储客户端
 *
 */
@Slf4j
@ConditionalOnClass(FTPClient.class)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX + "." + FileServerProperties.TYPE_FTP, name = FileServerProperties.ENABLED, havingValue = FileServerProperties.HAVING_VALUE)
public class FtpTemplate {

    @Resource
    private FileServerProperties fileProperties;

    /**
     * 创建连接
     *
     * @return
     */
    private FTPClient connectFtp() {
        FTPClient ftpClient;
        try {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(fileProperties.getFtp().getTimeout());
            ftpClient.connect(fileProperties.getFtp().getHost(), fileProperties.getFtp().getPort());
            ftpClient.login(fileProperties.getFtp().getUsername(), fileProperties.getFtp().getPassword());
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new RuntimeException("用户名或密码错误");
            }
            log.info("FTP连接成功!");
        } catch (Exception e) {
            log.info("登陆FTP失败", e);
            throw new RuntimeException("登陆FTP失败：" + e.getCause().getMessage());
        }
        return ftpClient;
    }

    /**
     * 关闭FTP连接
     *
     * @param ftpClient
     */
    private void closeConnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error("关闭FTP连接失败", e);
            }
        }
    }

    /**
     * 上传文件
     *
     * @param multipartFile 文件对象
     * @param filePath      文件路径
     * @return 文件路径
     */
    public String uploadFile(MultipartFile multipartFile, String filePath) {
        FTPClient ftpClient = connectFtp();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String fileName = FileOssUtil.getFileName(filePath);
            String dirsPath = toPathOrCreateDir(ftpClient, filePath);
            fileName = URLEncoder.encode(fileName, "UTF-8");
            ftpClient.storeFile(fileName, inputStream);
            ftpClient.logout();
            log.info("文件上传成功");
            return dirsPath + "/" + fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("文件上传失败");
        } finally {
            closeConnect(ftpClient);
        }
    }

    /**
     * 切换文件夹
     *
     * @param ftp
     * @param filePath
     * @return
     * @throws IOException
     */
    private String toPathOrCreateDir(FTPClient ftp, String filePath) throws IOException {
        String basePath = fileProperties.getFtp().getBasePath();
        String extPath = StrUtil.subBefore(filePath, "/", true);
        String dirPath = basePath;
        if (StrUtil.isNotEmpty(extPath)) {
            dirPath = basePath + "/" + extPath;
        }
        String[] dirs = dirPath.split("/");
        for (String dir : dirs) {
            if (StrUtil.isBlank(dir)) {
                continue;
            }
            if (!ftp.changeWorkingDirectory(dir)) {
                ftp.makeDirectory(dir);
                ftp.changeWorkingDirectory(dir);
            }
        }
        return dirPath;
    }

    /**
     * 删除文件
     *
     * @param ftpFilePath 文件路径
     * @return
     */
    public boolean deleteFile(String ftpFilePath) {
        FTPClient ftpClient = connectFtp();
        boolean result;
        try {
            result = ftpClient.deleteFile(ftpFilePath);
            ftpClient.logout();
            return result;
        } catch (Exception e) {
            log.error("FTP文件删除失败", e);
            throw new RuntimeException("文件删除失败");
        } finally {
            closeConnect(ftpClient);
        }
    }

    public InputStream downloadFile(String ftpFilePath) {
        FTPClient ftpClient = connectFtp();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            InputStream inputStream = ftpClient.retrieveFileStream(ftpFilePath);
            ftpClient.logout();
            return inputStream;
        } catch (Exception e) {
            log.error("FTP文件下载失败:", e);
            throw new RuntimeException("文件下载失败");
        } finally {
            closeConnect(ftpClient);
        }
    }

}
