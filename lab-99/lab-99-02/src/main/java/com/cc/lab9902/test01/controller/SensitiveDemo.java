package com.cc.lab9902.test01.controller;

import com.cc.lab9902.test01.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-13 11:23
 */
public class SensitiveDemo {
    public static void main(String[] args) throws JsonProcessingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setName("张三");
        userEntity.setMobile("18000000001");
        userEntity.setIdCard("420117200001011000008888");
        userEntity.setAge(20);
        userEntity.setSex("男");

        //通过jackson方式，将对象序列化成json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(userEntity));
    }
}
