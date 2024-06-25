package com.cc.lab9901.test04.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-25 19:40
 */
@Configuration
public class RestTemplateConfig {
    /**
     * 没有实例化RestTemplate时，初始化RestTemplate
     *
     * @return
     */
    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
