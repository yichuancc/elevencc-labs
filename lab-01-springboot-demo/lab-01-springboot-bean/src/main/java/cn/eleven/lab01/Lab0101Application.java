package cn.eleven.lab01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Lab0101Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Lab0101Application.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        System.out.println("所以beanNames个数：" + beanNames.length);
        for (String bn : beanNames) {
            System.out.println(bn);
        }
        Integer integer = Integer.valueOf(11);
        int i = integer;
    }

}
