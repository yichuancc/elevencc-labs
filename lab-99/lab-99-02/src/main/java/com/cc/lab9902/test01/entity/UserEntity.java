package com.cc.lab9902.test01.entity;

import com.cc.lab9902.test01.annotation.SensitiveWrapped;
import com.cc.lab9902.test01.enums.SensitiveEnum;
import lombok.Data;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-13 11:22
 */
@Data
public class UserEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    @SensitiveWrapped(SensitiveEnum.MOBILE_PHONE)
    private String mobile;

    /**
     * 身份证号码
     */
    @SensitiveWrapped(SensitiveEnum.ID_CARD)
    private String idCard;

    /**
     * 年龄
     */
    private String sex;

    /**
     * 性别
     */
    private int age;

    //省略get、set...
}