package com.cc.lab9902;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Lab9902Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab9902Application.class, args);
    }

//    test05使用
//    public static void main(String[] args) {
//        //创建一个Spring容器
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        //将 事件监听器 注册到容器中
//        applicationContext.register(Call119FireEventListener.class);
//        applicationContext.register(SavePersonFireEventListener.class);
//        applicationContext.refresh();
//
//        // 发布着火的事件，触发监听
//        applicationContext.publishEvent(new FireEvent("着火了"));
//    }

}
