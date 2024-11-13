package com.cc.lab9902.test01.annotation;

import com.cc.lab9902.test01.config.SensitiveSerialize;
import com.cc.lab9902.test01.enums.SensitiveEnum;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * https://mp.weixin.qq.com/s/xjnwBzNizMQO6S5WFOaf5A
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface SensitiveWrapped {
    /**
     * 脱敏类型
     * @return
     */
    SensitiveEnum value();
}
