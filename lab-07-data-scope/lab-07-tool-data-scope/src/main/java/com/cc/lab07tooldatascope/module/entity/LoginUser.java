package com.cc.lab07tooldatascope.module.entity;

import lombok.Data;

import java.util.Set;

/**
 * 登录用户信息
 */
@Data
public class LoginUser {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 账号
     */
    private String account;


    /**
     * 名称
     */
    private String userName;
    /**
     * 工号（关联人事信息）
     */
    private String employeeNum;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 当前登录角色ID
     */
    private String currentRoleId;

    /**
     * 当前登录角色Code
     */
    private String currentRoleCode;

    /**
     * 当前登录机构编码
     */
    private String currentOrgCode;

    /**
     * 角色code
     */
    private Set<String> roleCodes;

    /**
     * 拥有的权限
     */
    private Set<String> permissions;
}
