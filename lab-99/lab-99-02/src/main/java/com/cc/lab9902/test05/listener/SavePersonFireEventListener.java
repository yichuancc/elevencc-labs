package com.cc.lab9902.test05.listener;

import com.cc.lab9902.test05.event.FireEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-19 11:21
 */
@Component
public class SavePersonFireEventListener implements ApplicationListener<FireEvent> {
    @Override
    public void onApplicationEvent(FireEvent event) {
        System.out.println("救人");
    }
}