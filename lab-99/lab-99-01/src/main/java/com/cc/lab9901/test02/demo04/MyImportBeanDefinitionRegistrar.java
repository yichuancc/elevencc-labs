package com.cc.lab9901.test02.demo04;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-06 21:45
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //定义一个bean：Service1
        BeanDefinition service1BeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Service1.class).getBeanDefinition();
        //注册bean
        registry.registerBeanDefinition("service1", service1BeanDefinition);

        //定义一个bean：Service2，通过addPropertyReference注入service1
        BeanDefinition service2BeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Service2.class).
                addPropertyReference("service1", "service1").
                getBeanDefinition();
        //注册bean
        registry.registerBeanDefinition("service2", service2BeanDefinition);
    }
}
