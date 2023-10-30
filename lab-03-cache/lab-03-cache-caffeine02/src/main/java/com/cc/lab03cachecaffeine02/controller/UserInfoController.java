package com.cc.lab03cachecaffeine02.controller;

import com.cc.lab03cachecaffeine02.model.UserInfo;
import com.cc.lab03cachecaffeine02.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-10-30 18:45
 */
@RestController
@RequestMapping
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/get/user-info")
    public Object getUserInfo(@RequestParam(name = "id") Integer id) {
        UserInfo userInfo = userInfoService.getByName(id);
        if (userInfo == null) {
            return "没有该用户";
        }
        return userInfo;
    }

    @PostMapping("/create/user-info")
    public Object createUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.addUserInfo(userInfo);
        return "SUCCESS";
    }

    @PostMapping("/update/user-info")
    public Object updateUserInfo(@RequestBody UserInfo userInfo) {
        UserInfo newUserInfo = userInfoService.updateUserInfo(userInfo);
        if (newUserInfo == null) {
            return "不存在该用户";
        }
        return newUserInfo;
    }

    @PostMapping("/delete/user-info")
    public Object deleteUserInfo(@RequestParam(name = "id") Integer id) {
        userInfoService.deleteById(id);
        return "SUCCESS";
    }
}
