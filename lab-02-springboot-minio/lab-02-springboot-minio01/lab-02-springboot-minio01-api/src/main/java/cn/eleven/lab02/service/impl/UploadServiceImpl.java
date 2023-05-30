package cn.eleven.lab02.service.impl;



import cn.eleven.lab02.common.MinioUtils;
import cn.eleven.lab02.common.RedisRepo;
import cn.eleven.lab02.common.ResponseResult;
import cn.eleven.lab02.common.ResultCode;
import cn.eleven.lab02.pojo.*;
import cn.eleven.lab02.service.UploadService;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@Slf4j
@Service
public class UploadServiceImpl implements UploadService {


    @Resource
    private MinioUtils fileService;

    @Resource
    private RedisRepo redisRepo;


    /**
     * 通过 sha256 获取已上传的数据（断点续传）
     *
     * @param sha256 String
     * @return Mono<Map < String, Object>>
     */
    @Override
    public ResponseResult<Object> getByFileSha256(String sha256) {
        log.info("tip message: 通过 <{}> 查询数据是否存在", sha256);
        // 获取文件名称和id
        String value = redisRepo.get(sha256);
        FileUploadInfo fileUploadInfo = null;
        if (value != null) {
            fileUploadInfo = JSONObject.parseObject(value, FileUploadInfo.class);
        }
        if (fileUploadInfo == null) {
            // 返回数据不存在
            log.error("error message: 文件数据不存在");
            return ResponseResult.error(ResultCode.FOUND);
        }
        // 获取桶名称
        String bucketName = fileService.getBucketName(fileUploadInfo.getFileType());

        return fileService.getByFileSha256(fileUploadInfo.getFileName(), fileUploadInfo.getUploadId(), bucketName);
    }


    /**
     * 文件分片上传
     *
     * @param fileUploadInfo
     * @return Mono<Map < String, Object>>
     */
    @Override
    public ResponseResult<Object> initMultiPartUpload(FileUploadInfo fileUploadInfo) {
        log.info("tip message: 通过 <{}> 开始初始化<分片上传>任务", fileUploadInfo);
        // 获取桶
        String bucketName = fileService.getBucketName(fileUploadInfo.getFileType());

        // 单文件上传可拆分，这里只做演示，可直接上传完成
        if (fileUploadInfo.getPartCount() == 1) {
            log.info("tip message: 当前分片数量 <{}> 进行单文件上传", fileUploadInfo.getPartCount());
            return fileService.getUploadObjectUrl(fileUploadInfo.getFileName(), bucketName);
        }
        // 分片上传
        else {
            log.info("tip message: 当前分片数量 <{}> 进行分片上传", fileUploadInfo.getPartCount());
            return fileService.initMultiPartUpload(fileUploadInfo, fileUploadInfo.getFileName(), fileUploadInfo.getPartCount(), fileUploadInfo.getContentType(), bucketName);
        }
    }

    /**
     * 文件合并
     *
     * @param
     * @return boolean
     */
    @Override
    public boolean mergeMultipartUpload(FileUploadInfo fileUploadInfo) {
        log.info("tip message: 通过 <{}> 开始合并<分片上传>任务", fileUploadInfo);
        // 获取桶名称
        String bucketName = fileService.getBucketName(fileUploadInfo.getFileType());

        return fileService.mergeMultipartUpload(fileUploadInfo.getFileName(), fileUploadInfo.getUploadId(), bucketName);
    }




    @Override
    public String getFliePath(String bucketName, String fileName) {
        return fileService.getFliePath(bucketName, fileName);
    }

    @Override
    public String upload(MultipartFile file, String bucketName) {
        fileService.upload(file, bucketName);
        return getFliePath(bucketName, file.getName());
    }
}