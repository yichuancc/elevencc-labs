package com.cc.lab07tooldatascope.mybatis.datascope.interceptor;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 拓展分页拦截器
 *
 */
@Setter
public class MedicalPaginationInterceptor extends PaginationInnerInterceptor {

    /**
     * 查询拦截器
     */
    private QueryDataInterceptor[] queryDataInterceptors;


    @SneakyThrows
    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        QueryDataInterceptorExecutor.exec(queryDataInterceptors, executor, ms, parameter, rowBounds, resultHandler, boundSql);
        return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }
}
