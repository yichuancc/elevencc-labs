package com.cc.lab9902.test01.controller;

import com.cc.lab9902.test01.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-13 11:25
 */
@RequestMapping("/test01")
@RestController
public class Test01Controller {
    @RequestMapping("/test1")
    public UserEntity test1() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setName("张三");
        userEntity.setMobile("18000000001");
        userEntity.setIdCard("420117200001011000008888");
        userEntity.setAge(20);
        userEntity.setSex("男");
        return userEntity;
    }
}
