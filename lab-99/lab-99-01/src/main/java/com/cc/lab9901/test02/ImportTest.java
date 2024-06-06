package com.cc.lab9901.test02;

import com.cc.lab9901.test02.demo01.MainConfig1;
import com.cc.lab9901.test02.demo02.MainConfig2;
import com.cc.lab9901.test02.demo03.MainConfig3;
import com.cc.lab9901.test02.demo04.MainConfig4;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-06 21:10
 */
public class ImportTest {

    @Test
    public void test1() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
        //2.输出容器中定义的所有bean信息

        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.printf("%s->%s%n", beanName, context.getBean(beanName));
        }
    }

    @Test
    public void test2() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.printf("%s->%s%n", beanName, context.getBean(beanName));
        }
    }

    @Test
    public void test3() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.printf("%s->%s%n", beanName, context.getBean(beanName));
        }
    }

    @Test
    public void test4() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.printf("%s->%s%n", beanName, context.getBean(beanName));
        }
    }

}
