package com.cc.lab9902.test04.listener;

import com.cc.lab9902.test04.event.LoginEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @program: elevencc-labs
 * @description: // 日志处理事件监听器
 * @author: yic
 * @create: 2024-11-19 10:01
 */
@Component
public class LoginEventPrintLogListener {

    @EventListener
    public void handlerUserLoginEvent(LoginEvent event) {
        String userName = event.getUserName();
        // 在这里执行处理用户登录事件的逻辑，例如记录日志或触发其他操作
        System.out.println("User logged in: " + userName);
    }
}
