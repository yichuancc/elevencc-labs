package com.cc.lab9901.test02.demo02;

import org.springframework.context.annotation.Import;

/**
 * @program: elevencc-labs
 * @description: 通过Import来汇总多个@Configuration标注的配置类
 * @author: yic
 * @create: 2024-06-06 21:16
 */
@Import({ConfigModule1.class, ConfigModule2.class})
public class MainConfig2 {
}
