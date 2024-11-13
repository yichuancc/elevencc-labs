package com.cc.lab9902.test02.controller;

import com.cc.lab9902.test02.entity.People;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-13 11:25
 */
@RequestMapping("/test02")
@RestController
public class Test02Controller {
    @RequestMapping("/test1")
    public People test1() {
        People people = new People();
        people.setName("张三");
        people.setPhone("18000000001");
        people.setEmail("example@gmail.com");
        people.setAge(20);
        people.setSign("420117200001011000008888");
        people.setRemark("nihanikjj");
        return people;
    }
}
