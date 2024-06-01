package com.cc.file.template;


import com.cc.file.enums.PolicyType;
import com.cc.file.properties.FileServerProperties;
import com.cc.file.properties.MinIoProperties;
import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * minio配置
 *
 */
@Slf4j
@ConditionalOnClass(MinioClient.class)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX + "." + FileServerProperties.TYPE_MINIO, name = FileServerProperties.ENABLED, havingValue = FileServerProperties.HAVING_VALUE)
public class MinIoTemplate implements InitializingBean {

    @Resource
    private FileServerProperties fileProperties;

    /**
     * 失效时间
     */
    private final Integer expires = 60 * 60 * 12;

    /**
     * MinIO客户端
     */
    private MinioClient minioClient;

    /**
     * MinIO服务所在地址
     */
    private static String endpoint;

    /**
     * MinIO AccessKey
     */
    private static String accessKey;

    /**
     * MinIO secretKey
     */
    private static String secretKey;

    /**
     * 存储桶名称
     */
    private static String bucketName;

    /**
     * 初始化MinIO客户端
     *
     * @throws MinioException
     */
    @Override
    public void afterPropertiesSet() throws MinioException {
        MinIoProperties properties = fileProperties.getMinio();
        endpoint = properties.getEndpoint();
        accessKey = properties.getAccessKey();
        secretKey = properties.getSecretKey();
        bucketName = properties.getBucketName();
        if (ObjectUtils.isEmpty(endpoint)) {
            throw new MinioException("Minio-Endpoint未配置");
        }
        if (ObjectUtils.isEmpty(accessKey)) {
            throw new MinioException("Minio-AccessKey未配置");
        }
        if (ObjectUtils.isEmpty(secretKey)) {
            throw new MinioException("Minio-SecretKey密码未配置");
        }
        this.minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }

    /**
     * 判断桶是否存在
     *
     * @param bucketName
     * @return
     */
    public Boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("检查桶是否存在失败!", e);
        }
    }

    /**
     * 创建桶
     *
     * @param bucketName
     */
    public void createBucket(String bucketName) {
        if (!this.bucketExists(bucketName)) {
            try {
                //创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                throw new RuntimeException("创建桶失败!", e);
            }
        }
    }

    /**
     * 创建桶，并设置桶策略
     *
     * @param bucketName
     * @param policyType
     */
    public void createBucket(String bucketName, PolicyType policyType) {
        if (!this.bucketExists(bucketName)) {
            try {
                //创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                //设置通默认策略
                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder().bucket(bucketName)
                                .config(getPolicyType(bucketName, policyType)).build()
                );
            } catch (Exception e) {
                throw new RuntimeException("创建桶失败!", e);
            }
        }
    }


    /**
     * 上传文件
     *
     * @param bucketName
     * @param file
     * @param objectName
     * @return
     */
    public String uploadFile(String bucketName, String objectName, MultipartFile file) {
        return putMultipartFile(bucketName, objectName, file);
    }

    /**
     * 上传MultipartFile通用方法
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @param file       文件
     */
    private String putMultipartFile(String bucketName, String objectName, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // 先创建桶
            this.createBucket(bucketName);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(file.getContentType())
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
            return bucketName.concat("/").concat(objectName);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectName
     */
    public void removeFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new RuntimeException("删除文件失败!");
        }
    }

    /**
     * 下载文件
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            // 判断对象是否存在。如果不存在, statObject()抛出异常
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());

            // 获取对象流。
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw new RuntimeException("下载文件失败");
        }
    }

    /**
     * 获取存储桶策略
     *
     * @param bucketName 存储桶名称
     * @param policyType 策略枚举
     * @return String
     */
    private static String getPolicyType(String bucketName, PolicyType policyType) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": [\n");

        switch (policyType) {
            case WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            case READ_WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucket\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            default:
                builder.append("                \"s3:GetBucketLocation\"\n");
                break;
        }

        builder.append("            ],\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("\"\n");
        builder.append("        },\n");
        if (PolicyType.READ.equals(policyType)) {
            builder.append("        {\n");
            builder.append("            \"Action\": [\n");
            builder.append("                \"s3:ListBucket\"\n");
            builder.append("            ],\n");
            builder.append("            \"Effect\": \"Deny\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::");
            builder.append(bucketName);
            builder.append("\"\n");
            builder.append("        },\n");

        }
        builder.append("        {\n");
        builder.append("            \"Action\": ");

        switch (policyType) {
            case WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            case READ_WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:GetObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            default:
                builder.append("\"s3:GetObject\",\n");
                break;
        }

        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("/*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");
        return builder.toString();
    }

}
