package cn.eleven.lab02.controller;
import cn.eleven.lab02.pojo.FileUploadInfo;
import cn.eleven.lab02.common.MinioUtils;
import cn.eleven.lab02.common.ResponseResult;
import cn.eleven.lab02.common.ResultCode;
import cn.eleven.lab02.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * minio上传流程
 *
 * 1.检查数据库中是否存在上传文件
 *
 * 2.根据文件信息初始化，获取分片预签名url地址，前端根据url地址上传文件
 *
 * 3.上传完成后，将分片上传的文件进行合并
 *
 * 4.保存文件信息到数据库
 */
@RestController
@Slf4j
public class FileMinioController {

    @Resource
    private UploadService uploadService;

    //@Resource
    //private VideoFileInfoService videoFileInfoService;

    @Resource
    private MinioUtils minioUtils;

    /**
     * 校验文件是否存在
     *
     * @param md5 String
     * @return ResponseResult<Object>
     */
    @GetMapping("/multipart/check")
    public ResponseResult checkFileUploadedByMd5(@RequestParam("md5") String md5) {
        log.info("REST: 通过查询 <{}> 文件是否存在、是否进行断点续传", md5);
        if (StringUtils.isEmpty(md5)) {
            log.error("查询文件是否存在、入参无效");
            return ResponseResult.error(ResultCode.ACCESS_PARAMETER_INVALID);
        }
        return uploadService.getByFileSha256(md5);
    }

    /**
     * 分片初始化
     *
     * @param fileUploadInfo 文件信息
     * @return ResponseResult<Object>
     */
    @PostMapping("/multipart/init")
    public ResponseResult initMultiPartUpload(@RequestBody FileUploadInfo fileUploadInfo) {
        log.info("REST: 通过 <{}> 初始化上传任务", fileUploadInfo);
        return uploadService.initMultiPartUpload(fileUploadInfo);
    }

    /**
     * 完成上传
     *
     * @param fileUploadInfo  文件信息
     * @return ResponseResult<Object>
     */
    @PostMapping("/multipart/merge")
    public ResponseResult completeMultiPartUpload(@RequestBody FileUploadInfo fileUploadInfo) {
        log.info("REST: 通过 {} 合并上传任务", fileUploadInfo);
        //Map<String, Object> resMap = new HashMap<>();
        //合并文件
        boolean result = uploadService.mergeMultipartUpload(fileUploadInfo);
        //获取上传文件地址
        if(result){
            String fliePath = uploadService.getFliePath(fileUploadInfo.getFileType().toLowerCase(), fileUploadInfo.getFileName());
            return ResponseResult.success(fliePath);
        }
        return ResponseResult.error();
    }


    /**
     * 保存文件信息到数据库
     * @param fileInfo 文件信息
     * @return
     */
    @PostMapping("/multipart/uploadFileInfo")
    public ResponseResult uploadFileInfo(@RequestBody FileInfo fileInfo){
        log.info("REST: 上传文件信息 <{}> ", fileInfo);
        if(fileInfo ==null){
            return ResponseResult.error(ResultCode.ACCESS_PARAMETER_INVALID);
        }else{
            //FileInfo insert = videoFileInfoService.insert(fileInfo);
        }
        return ResponseResult.success();
    }


    @PostMapping("/multipart/uploadScreenshot")
    public ResponseResult uploaduploadScreenshot(@RequestPart("photos") MultipartFile[] photos,
                                                 @RequestParam("buckName") String buckName){
        log.info("REST: 上传文件信息 <{}> ", photos);

        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                uploadService.upload(photo,buckName);
            }
        }
        return ResponseResult.success();
    }


    @RequestMapping("/createBucket")
    public void createBucket(@RequestParam("bucketName")String bucketName){
        String bucket = minioUtils.createBucket(bucketName);
    }

}




