package com.cc.lab9902.test03.enums;

import lombok.Getter;

/**
 * 隐私数据类型枚举
 *
 * @author cc
 */
@Getter
public enum PrivacyTypeEnum {

    /**
     * 自定义（此项可自定义脱敏范围）
     */
    CUSTOMER,

    /**
     * 姓名
     */
    NAME,

    /**
     * 身份证号
     */
    ID_CARD,

    /**
     * 手机号
     */
    PHONE,

    /**
     * 银行卡号
     */
    BANK_CARD,

    /**
     * 邮箱
     */
    EMAIL
}
