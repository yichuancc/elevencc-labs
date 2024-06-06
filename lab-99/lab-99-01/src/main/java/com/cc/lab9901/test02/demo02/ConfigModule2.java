package com.cc.lab9901.test02.demo02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-06 21:15
 */
@Configuration
public class ConfigModule2 {
    @Bean
    public String module2() {
        return "我是模块2配置类";
    }
}
