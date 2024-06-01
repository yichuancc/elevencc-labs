package com.cc.lab07tooldatascope.mybatis.datascope.handler;

import com.cc.lab07tooldatascope.module.entity.LoginUser;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;

/**
 * 数据权限规则
 *
 */
public interface DataScopeHandler {

    /**
     * 获取过滤sql
     *
     * @param mapperId    数据查询类
     * @param dataScope   数据权限类
     * @param loginUser   当前用户信息
     * @param originalSql 原始Sql
     * @return sql
     */
    String sqlCondition(String mapperId, DataScopeModel dataScope, LoginUser loginUser, String originalSql);

}
