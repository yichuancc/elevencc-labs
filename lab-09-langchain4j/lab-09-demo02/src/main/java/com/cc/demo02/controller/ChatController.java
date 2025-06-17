package com.cc.demo02.controller;

import com.cc.demo02.bean.ChatMessages;
import com.cc.demo02.service.SeparateChatAssistant;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


/**
 * @author cc
 */
@Slf4j
@RestController
public class ChatController {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatMessages chatForm) {
        return separateChatAssistant.chatFlux(chatForm.getMessageId(), chatForm.getContent());
    }
}
