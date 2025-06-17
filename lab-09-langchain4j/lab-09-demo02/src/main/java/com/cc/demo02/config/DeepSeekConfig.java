package com.cc.demo02.config;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeepSeekConfig {
    @Bean
    public StreamingChatLanguageModel deepSeekStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey(System.getenv("DEEPSEEK_API_KEY"))  // 从环境变量读取
                .modelName("deepseek-chat")
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
