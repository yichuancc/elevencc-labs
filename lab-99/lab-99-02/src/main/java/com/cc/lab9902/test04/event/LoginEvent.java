package com.cc.lab9902.test04.event;

import org.springframework.context.ApplicationEvent;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-19 09:56
 */
public class LoginEvent extends ApplicationEvent {

    private final String userName;

    public LoginEvent(Object source, String userName) {
        super(source);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
