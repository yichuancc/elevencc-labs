package com.cc.lab07tooldatascope.mybatis.datascope.config;

import com.cc.lab07tooldatascope.mybatis.datascope.handler.DataScopeHandler;
import com.cc.lab07tooldatascope.mybatis.datascope.handler.MedicalDataScopeHandler;
import com.cc.lab07tooldatascope.mybatis.datascope.handler.MedicalScopeModelHandler;
import com.cc.lab07tooldatascope.mybatis.datascope.handler.ScopeModelHandler;
import com.cc.lab07tooldatascope.mybatis.datascope.interceptor.DataScopeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据权限配置类
 *
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DataScopeProperties.class)
public class DataScopeConfiguration {

    @Bean
    @ConditionalOnMissingBean(ScopeModelHandler.class)
    public ScopeModelHandler scopeModelHandler() {
        return new MedicalScopeModelHandler();
    }

    @Bean
    @ConditionalOnBean(ScopeModelHandler.class)
    @ConditionalOnMissingBean(DataScopeHandler.class)
    public DataScopeHandler dataScopeHandler(ScopeModelHandler scopeModelHandler) {
        return new MedicalDataScopeHandler(scopeModelHandler);
    }


    @Bean
    @ConditionalOnBean(DataScopeHandler.class)
    @ConditionalOnMissingBean(DataScopeInterceptor.class)
    public DataScopeInterceptor interceptor(DataScopeHandler dataScopeHandler, DataScopeProperties dataScopeProperties) {
        return new DataScopeInterceptor(dataScopeHandler, dataScopeProperties);
    }

}
