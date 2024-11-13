package com.cc.lab9902.test02.entity;

import com.cc.lab9902.test02.annotation.PrivacyEncrypt;
import com.cc.lab9902.test02.enums.PrivacyTypeEnum;
import lombok.Data;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-13 13:17
 */
@Data
public class People {

    private Integer id;

    @PrivacyEncrypt(type = PrivacyTypeEnum.NAME) // 隐藏名字
    private String name;

    private Integer sex;

    private Integer age;

    @PrivacyEncrypt(type = PrivacyTypeEnum.PHONE) // 隐藏手机号
    private String phone;

    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL) // 隐藏邮箱
    private String email;

    @PrivacyEncrypt(type = PrivacyTypeEnum.ID_CARD) // 隐藏身份证号码
    private String sign;

    @PrivacyEncrypt(type = PrivacyTypeEnum.CUSTOMER, symbol = "+", suffixNoMaskLen = 2, prefixNoMaskLen = 2) // 隐藏身份证号码
    private String remark;
}
