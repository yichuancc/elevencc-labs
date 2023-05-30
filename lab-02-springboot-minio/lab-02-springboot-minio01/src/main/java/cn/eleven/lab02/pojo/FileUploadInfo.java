package cn.eleven.lab02.pojo;


import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class FileUploadInfo {

    private String fileName;

    private Double fileSize;

    private String contentType;

    private Integer partCount;

    private String uploadId;

    // 桶名称
    //private String bucketName;

    //md5
    private String fileMd5;

    //文件类型
    private String fileType;

    public FileUploadInfo() {
    }
}

