package com.cc.demo02.service;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

//因为我们在配置文件中同时配置了多个大语言模型，所以需要在这里明确指定（EXPLICIT）模型的beanName
@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel")
public interface Assistant01 {
    String chat(String userMessage);
}
