package com.cc.lab03cachecaffeine01.service;

import com.cc.lab03cachecaffeine01.model.UserInfo;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-10-30 18:37
 */
public interface UserInfoService {
    /**
     * 增加用户信息
     *
     * @param userInfo 用户信息
     */
    void addUserInfo(UserInfo userInfo);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfo getByName(Integer id);

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 用户信息
     */
    UserInfo updateUserInfo(UserInfo userInfo);

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     */
    void deleteById(Integer id);
}
