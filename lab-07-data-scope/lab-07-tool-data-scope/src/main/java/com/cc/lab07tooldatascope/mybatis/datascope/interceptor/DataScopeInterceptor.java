package com.cc.lab07tooldatascope.mybatis.datascope.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cc.lab07tooldatascope.module.entity.LoginUser;
import com.cc.lab07tooldatascope.mybatis.datascope.annotation.DataAuth;
import com.cc.lab07tooldatascope.mybatis.datascope.config.DataScopeProperties;
import com.cc.lab07tooldatascope.mybatis.datascope.constant.DataScopeConstant;
import com.cc.lab07tooldatascope.mybatis.datascope.handler.DataScopeHandler;
import com.cc.lab07tooldatascope.mybatis.datascope.model.DataScopeModel;
import com.cc.lab07tooldatascope.mybatis.datascope.utils.DataScopeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * mybatis 数据权限拦截器
 *
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes"})
public class DataScopeInterceptor implements QueryDataInterceptor {

    private final ConcurrentMap<String, DataAuth> dataAuthMap = new ConcurrentHashMap<>(8);
    private final DataScopeHandler dataScopeHandler;
    private final DataScopeProperties dataScopeProperties;

    @Override
    public void intercept(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        //未启用则放行
        if (!dataScopeProperties.getEnabled()) {
            return;
        }

        //未取到用户则放行
        LoginUser loginUser;
        try {
            // 获取当前登录用户，此处模拟用户
            loginUser = new LoginUser();
            loginUser.setUserId("1");
            loginUser.setCurrentRoleId("1");
            if (Objects.isNull(loginUser)) {
                return;
            }
        } catch (Exception e) {
            return;
        }

        if (SqlCommandType.SELECT != ms.getSqlCommandType() || StatementType.CALLABLE == ms.getStatementType()) {
            return;
        }

        String originalSql = boundSql.getSql();

        //查找注解中包含DataAuth类型的参数
        DataAuth dataAuth = findDataAuthAnnotation(ms);

        //注解为空并且数据权限方法名未匹配到,则放行
        String mapperId = ms.getId();
        String className = mapperId.substring(0, mapperId.lastIndexOf(DataScopeConstant.DOT));
        String mapperName = ClassUtils.getShortName(className);
        String methodName = mapperId.substring(mapperId.lastIndexOf(DataScopeConstant.DOT) + 1);
        List<String> mapperKey = dataScopeProperties.getMapperKey();
        List<String> mapperExclude = dataScopeProperties.getMapperExclude();
        boolean keyFlag = false;
        boolean excludeFlag = false;
        if (CollectionUtils.isNotEmpty(mapperKey)) {
            keyFlag = mapperKey.stream().noneMatch(methodName::contains);
        }
        if (CollectionUtils.isNotEmpty(mapperExclude)) {
            excludeFlag = mapperExclude.stream().anyMatch(mapperName::contains);
        }
        boolean mapperSkip = keyFlag || excludeFlag;
        if (dataAuth == null && mapperSkip) {
            return;
        }

        //创建数据权限模型
        DataScopeModel dataScope = new DataScopeModel();

        //若注解不为空,则配置注解项
        if (dataAuth != null) {
            dataScope.setScopeColumn(dataAuth.column());
            dataScope.setScopeType(dataAuth.type().getType());
            dataScope.setScopeField(dataAuth.field());
            dataScope.setScopeValue(dataAuth.value());
        }

        //获取数据权限规则对应的筛选Sql
        String sqlCondition = dataScopeHandler.sqlCondition(mapperId, dataScope, loginUser, originalSql);
        if (!DataScopeUtil.isBlank(sqlCondition)) {
            PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
            mpBoundSql.sql(sqlCondition);
        }
    }

    /**
     * 获取数据权限注解信息
     *
     * @param mappedStatement mappedStatement
     * @return DataAuth
     */
    private DataAuth findDataAuthAnnotation(MappedStatement mappedStatement) {
        String id = mappedStatement.getId();
        return dataAuthMap.computeIfAbsent(id, (key) -> {
            String className = key.substring(0, key.lastIndexOf(StringPool.DOT));
            String mapperBean = DataScopeUtil.firstCharToLower(ClassUtils.getShortName(className));
            Object mapper = SpringUtil.getBean(mapperBean);
            String methodName = key.substring(key.lastIndexOf(StringPool.DOT) + 1);
            Class<?>[] interfaces = ClassUtils.getAllInterfaces(mapper);
            for (Class<?> mapperInterface : interfaces) {
                for (Method method : mapperInterface.getDeclaredMethods()) {
                    if (methodName.equals(method.getName()) && method.isAnnotationPresent(DataAuth.class)) {
                        return method.getAnnotation(DataAuth.class);
                    }
                }
            }
            return null;
        });
    }

}
