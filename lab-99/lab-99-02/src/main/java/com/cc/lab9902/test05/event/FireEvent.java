package com.cc.lab9902.test05.event;

import org.springframework.context.ApplicationEvent;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-19 11:17
 */
//火宅事件
public class FireEvent extends ApplicationEvent {
    public FireEvent(Object source) {
        super(source);
    }
}
