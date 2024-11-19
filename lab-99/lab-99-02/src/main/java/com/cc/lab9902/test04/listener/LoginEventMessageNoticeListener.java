package com.cc.lab9902.test04.listener;

import com.cc.lab9902.test04.event.LoginEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @program: elevencc-labs
 * @description: 登录消息通知事件监听器
 * @author: yic
 * @create: 2024-11-19 10:03
 */
@Component
public class LoginEventMessageNoticeListener {
    @EventListener
    public void LoginEventMessageNoticeListener(LoginEvent event) {
        String username = event.getUserName();
        // 发送消息通知用户
        System.out.println("message send User logged in: " + username);
    }
}
