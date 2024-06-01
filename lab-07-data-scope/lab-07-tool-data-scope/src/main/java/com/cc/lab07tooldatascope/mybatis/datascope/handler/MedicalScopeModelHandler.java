package com.cc.lab07tooldatascope.mybatis.datascope.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.cc.lab07tooldatascope.module.service.SysRoleDataScopeService;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 */
@RequiredArgsConstructor
public class MedicalScopeModelHandler implements ScopeModelHandler {

    private static final DataScopeModel SEARCHED_DATA_SCOPE_MODEL = new DataScopeModel(Boolean.TRUE);

    /**
     * 获取数据权限
     *
     * @param mapperId 数据权限mapperId
     * @param roleId   用户角色集合
     * @return DataScopeModel
     */
    @Override
    public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
        DataScopeModel dataScope;
        List<DataScopeModel> list = SpringUtil.getBean(SysRoleDataScopeService.class).dataByMapper(mapperId, roleId);
        if (CollectionUtil.isNotEmpty(list)) {
            dataScope = list.iterator().next();
            dataScope.setSearched(Boolean.TRUE);
        } else {
            dataScope = SEARCHED_DATA_SCOPE_MODEL;
        }
        return StrUtil.isNotBlank(dataScope.getId()) ? dataScope : null;
    }

    @Override
    public Set<String> getOrgAndOrgChild(String orgCode) {
        // 编写具体逻辑
        return new HashSet<>();
    }


}
