package com.cc.lab07tooldatascope.mybatis.datascope.interceptor;

import cn.hutool.core.util.ObjectUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 查询拦截器执行器
 *
 */
@SuppressWarnings({"rawtypes"})
public class QueryDataInterceptorExecutor {

    /**
     * 执行查询拦截器
     */
    static void exec(QueryDataInterceptor[] interceptors, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws Throwable {
        if (ObjectUtil.isEmpty(interceptors)) {
            return;
        }
        for (QueryDataInterceptor interceptor : interceptors) {
            interceptor.intercept(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
    }

}
