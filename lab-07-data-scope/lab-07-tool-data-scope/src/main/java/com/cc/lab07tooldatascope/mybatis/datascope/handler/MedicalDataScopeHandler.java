package com.cc.lab07tooldatascope.mybatis.datascope.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cc.lab07tooldatascope.module.entity.LoginUser;
import com.cc.lab07tooldatascope.mybatis.datascope.constant.DataScopeEnum;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;
import com.cc.lab07tooldatascope.mybatis.datascope.utils.DataScopeUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * 默认数据权限规则
 *
 */
@RequiredArgsConstructor
public class MedicalDataScopeHandler implements DataScopeHandler {

    private final ScopeModelHandler scopeModelHandler;


    @Override
    public String sqlCondition(String mapperId, DataScopeModel dataScope, LoginUser loginUser, String originalSql) {


        //根据mapperId从数据库中获取对应模型
        DataScopeModel dataScopeDb = scopeModelHandler.getDataScopeByMapper(mapperId, loginUser.getCurrentRoleId());

        //未从数据库找到对应配置则采用默认
        dataScope = (dataScopeDb != null) ? dataScopeDb : dataScope;

        //判断数据权限类型并组装对应Sql
        String scopeRule = Objects.requireNonNull(dataScope).getScopeType();
        DataScopeEnum scopeTypeEnum = DataScopeEnum.of(scopeRule);
        Set<String> ids = new HashSet<>();
        String whereSql = "where scope.{} in ({})";
        if (DataScopeEnum.ALL == scopeTypeEnum) {
            return null;
        } else if (DataScopeEnum.OWN == scopeTypeEnum) {
            ids.add(loginUser.getUserId());
        } else if (DataScopeEnum.OWN_ORG == scopeTypeEnum) {
            ids.add(loginUser.getCurrentOrgCode());
        } else if (DataScopeEnum.OWN_ORG_CHILD == scopeTypeEnum) {
            Set<String> orgCodes = scopeModelHandler.getOrgAndOrgChild(loginUser.getCurrentOrgCode());
            ids.addAll(orgCodes);
        } else if (DataScopeEnum.CUSTOM_ORG == scopeTypeEnum) {
            // 如果不存在，就查找所在机构
            List<String> orgCodes = Arrays.asList(dataScope.getScopeValue().split(StrPool.COMMA));
            if (CollectionUtils.isEmpty(orgCodes)) {
                ids.add(loginUser.getCurrentOrgCode());
            } else {
                ids.addAll(orgCodes);
            }
        } else if (DataScopeEnum.CUSTOM_USER == scopeTypeEnum) {
            // 如果不存在，就查找当前用户
            List<String> userIds = Arrays.asList(dataScope.getScopeValue().split(StrPool.COMMA));
            if (CollectionUtils.isEmpty(userIds)) {
                ids.add(loginUser.getUserId());
            } else {
                ids.addAll(userIds);
            }
        } else if (DataScopeEnum.CUSTOM_SQL == scopeTypeEnum) {
            whereSql = StrUtil.format(dataScope.getScopeValue(), BeanUtil.beanToMap(loginUser));
        }
        return DataScopeUtil.format("select {} from ({}) scope " + whereSql, DataScopeUtil.toStr(dataScope.getScopeField(), "*"), originalSql, dataScope.getScopeColumn(), DataScopeUtil.join(DataScopeUtil.joinSingleQuotes(ids)));
    }

}
