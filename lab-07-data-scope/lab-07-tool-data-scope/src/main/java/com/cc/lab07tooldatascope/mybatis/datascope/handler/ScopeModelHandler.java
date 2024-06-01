package com.cc.lab07tooldatascope.mybatis.datascope.handler;


import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;

import java.util.Set;

/**
 * 获取数据权限模型统一接口
 *
 */
public interface ScopeModelHandler {

    /**
     * 获取数据权限
     *
     * @param mapperId 数据权限mapperId
     * @param roleId   用户角色集合
     * @return DataScopeModel
     */
    DataScopeModel getDataScopeByMapper(String mapperId, String roleId);

    /**
     * 获取本机构和机构子级
     *
     * @param orgCode
     * @return
     */
    Set<String> getOrgAndOrgChild(String orgCode);
}
