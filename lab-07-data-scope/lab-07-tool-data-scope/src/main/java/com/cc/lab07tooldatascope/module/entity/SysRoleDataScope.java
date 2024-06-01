package com.cc.lab07tooldatascope.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role_data_scope")
public class SysRoleDataScope {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 数据权限id
     */
    private String dataScopeId;
    /**
     * 角色id
     */
    private String roleId;
}
