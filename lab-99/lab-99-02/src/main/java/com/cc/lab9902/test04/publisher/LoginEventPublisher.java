package com.cc.lab9902.test04.publisher;

import com.cc.lab9902.test04.event.LoginEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-19 09:57
 */
@Service
public class LoginEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public LoginEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishLoginEvent(String userName) {
        LoginEvent loginEvent = new LoginEvent(this, userName);
        applicationEventPublisher.publishEvent(loginEvent);
    }
}
