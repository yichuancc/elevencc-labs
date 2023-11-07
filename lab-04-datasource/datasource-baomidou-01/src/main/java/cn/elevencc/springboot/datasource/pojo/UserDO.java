package cn.elevencc.springboot.datasource.pojo;

import lombok.Data;

/**
 * 用户 DO
 */
@Data
public class UserDO {

    /**
     * 用户编号
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;
}
