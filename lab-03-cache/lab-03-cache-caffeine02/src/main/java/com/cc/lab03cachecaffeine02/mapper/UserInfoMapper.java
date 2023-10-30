package com.cc.lab03cachecaffeine02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lab03cachecaffeine02.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-10-30 19:57
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
