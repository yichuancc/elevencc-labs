package com.cc.lab9902.test04.command;

import com.cc.lab9902.test04.publisher.LoginEventPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-19 10:04
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private final LoginEventPublisher loginEventPublisher;

    public MyCommandLineRunner(LoginEventPublisher loginEventPublisher) {
        this.loginEventPublisher = loginEventPublisher;
    }

    @Override
    public void run(String... args) throws Exception {
        // 在应用程序启动后执行的自定义逻辑
        System.out.println("MyCommandLineRunner executed!");
        // 登录成功
        // 登录执行后逻辑
        loginEventPublisher.publishLoginEvent("eleven");
    }
}
