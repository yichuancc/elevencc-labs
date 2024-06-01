package com.cc.lab07tooldatascope.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_data_scope")
public class SysDataScope {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 菜单主键
     */
    private String menuId;
    /**
     * 数据权限名称
     */
    private String scopeName;
    /**
     * 数据权限可见字段
     */
    private String scopeField;
    /**
     * 数据权限类名
     */
    private String scopeClass;
    /**
     * 数据权限字段
     */
    private String scopeColumn;
    /**
     * 数据权限规则类型
     */
    private String scopeType;
    /**
     * 数据权限值域
     */
    private String scopeValue;
    /**
     * 数据权限备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人ID
     */
    private String createUser;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人ID
     */
    private String updateUser;

    /**
     * 更新人名称
     */
    private String updateName;

    /**
     * 是否删除，默认0，已删除1
     */
    private String delFlag;
}
