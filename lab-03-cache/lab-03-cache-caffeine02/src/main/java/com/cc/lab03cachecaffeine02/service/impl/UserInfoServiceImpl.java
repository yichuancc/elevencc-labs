package com.cc.lab03cachecaffeine02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lab03cachecaffeine02.mapper.UserInfoMapper;
import com.cc.lab03cachecaffeine02.model.UserInfo;
import com.cc.lab03cachecaffeine02.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-10-30 18:38
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    @CachePut(value = "users", key = "#userInfo.id")
    public void addUserInfo(UserInfo userInfo) {
        log.info("create");
        this.save(userInfo);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserInfo getByName(Integer id) {
        log.info("get");
        return this.getById(id);
    }

    @Override
    @CachePut(value = "users", key = "#userInfo.id")
    public UserInfo updateUserInfo(UserInfo userInfo) {
        log.info("update");
        this.updateById(userInfo);
        return userInfo;
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public void deleteById(Integer id) {
        log.info("delete");
        this.removeById(id);
    }
}
