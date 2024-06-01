package com.cc.lab07tooldatascope.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.cc.lab07tooldatascope.mybatis.datascope.interceptor.MedicalPaginationInterceptor;
import com.cc.lab07tooldatascope.mybatis.datascope.interceptor.QueryDataInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * MybatisPlus配置
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
public class MybatisPluginConfiguration {

    /**
     * 拦截器集合
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<QueryDataInterceptor[]> queryDataInterceptors) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 配置分页拦截器
        MedicalPaginationInterceptor paginationInnerInterceptor = new MedicalPaginationInterceptor();
        // 配置自定义查询拦截器
        QueryDataInterceptor[] queryDataInterceptorArray = queryDataInterceptors.getIfAvailable();
        if (ObjectUtils.isNotEmpty(queryDataInterceptorArray)) {
            AnnotationAwareOrderComparator.sort(queryDataInterceptorArray);
            paginationInnerInterceptor.setQueryDataInterceptors(queryDataInterceptorArray);
        }
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 这行代码设置了分页插件的最大限制为500条数据，分页查询时，最多只能查询到500条数据
        paginationInnerInterceptor.setMaxLimit(500L);
        // 查询结果超出了最大限制，插件会截断返回结果，而不会抛出异常。
        paginationInnerInterceptor.setOverflow(false);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
