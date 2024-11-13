package com.cc.lab9902.test03.annotation;

import com.cc.lab9902.test03.config.PrivacySerializer;
import com.cc.lab9902.test03.enums.PrivacyTypeEnum;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义数据脱敏注解
 *
 * @author cc
 */
@Target(ElementType.FIELD) // 作用在字段上
@Retention(RetentionPolicy.RUNTIME) // class文件中保留，运行时也保留，能通过反射读取
@JacksonAnnotationsInside // 表示自定义注解PrivacyEncrypt
@JsonSerialize(using = PrivacySerializer.class) // 该注解使用序列化的方式
public @interface PrivacyEncrypt {

    /**
     * 脱敏类型（无默认值，使用时必须指定type）
     */
    PrivacyTypeEnum type();

    /**
     * 前缀保留长度
     */
    int prefixNoMaskLen() default 1;

    /**
     * 后缀保留长度
     */
    int suffixNoMaskLen() default 1;

    /**
     * 替换符
     */
    String symbol() default "*";
}
