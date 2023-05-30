package cn.eleven.lab02.common;


/**
 * http状态码枚举类
 */
public enum ResultCode {

    SUCCESS(1, "Success", "成功"),
    UPLOADING(2, "Uploading", "上传中"),
    FAIL(-1, "Err", "失败"),
    FOUND(302, "Found", "请重新发送请求"),
    ACCESS_PARAMETER_INVALID(1001, "Invalid access parameter", "访问参数无效"),
    UPLOAD_FILE_FAILED(1002, "File upload failure", "文件上传失败"),
    DATA_NOT_EXISTS(1003, "Data does not exist", "数据不存在");

    private int code;
    private String enMessage;
    private String zhMessage;

    ResultCode(int code, String enMessage, String zhMessage) {
        this.code = code;
        this.enMessage = enMessage;
        this.zhMessage = zhMessage;
    }

    ResultCode(int code, String message) {

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEnMessage() {
        return enMessage;
    }

    public void setEnMessage(String enMessage) {
        this.enMessage = enMessage;
    }

    public String getZhMessage() {
        return zhMessage;
    }

    public void setZhMessage(String zhMessage) {
        this.zhMessage = zhMessage;
    }
}
