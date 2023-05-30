package cn.eleven.lab02.service;

import cn.eleven.lab02.pojo.FileUploadInfo;
import cn.eleven.lab02.common.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 分片上传初始化
     *
     * @param fileUploadInfo
     * @return Map<String, Object>
     */
    ResponseResult<Object> initMultiPartUpload(FileUploadInfo fileUploadInfo);

    /**
     * 完成分片上传
     *
     * @param  fileUploadInfo
     * @return boolean
     */
    boolean mergeMultipartUpload(FileUploadInfo fileUploadInfo);

    /**
     *  通过 sha256 获取已上传的数据
     * @param sha256 String
     * @return Mono<Map<String, Object>>
     */
    ResponseResult<Object> getByFileSha256(String sha256);

    /**
     *  获取文件地址
     * @param bucketName
     * @param fileName
     *
     */
    String getFliePath(String bucketName, String fileName);


    /**
     * 单文件上传
     * @param file
     * @param bucketName
     * @return
     */
    String upload(MultipartFile file, String bucketName);

}