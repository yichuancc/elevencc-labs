package com.cc.lab07tooldatascope.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lab07tooldatascope.module.entity.SysRoleDataScope;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;

import java.util.List;

public interface SysRoleDataScopeService extends IService<SysRoleDataScope> {
    /**
     * 根据mapperId获取数据权限配置
     *
     * @param mapperId
     * @param roleId
     * @return
     */
    List<DataScopeModel> dataByMapper(String mapperId, String roleId);

}

