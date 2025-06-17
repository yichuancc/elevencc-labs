package com.cc.demo02.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

//因为我们在配置文件中同时配置了多个大语言模型，所以需要在这里明确指定（EXPLICIT）模型的beanName
@AiService(wiringMode = EXPLICIT,
        streamingChatModel = "deepSeekStreamingChatModel",
        chatModel = "openAiChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        chatMemory = "chatMemory")
public interface SeparateChatAssistant {
    /**
     * 分离聊天记录
     *
     * @param memoryId    聊天id
     * @param userMessage 用户消息
     * @return
     */
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    /**
     * 分离聊天记录
     * -- @SystemMessage 设定角色，塑造AI助手的专业身份，明确助手的能力范围
     * -- @SystemMessage 的内容将在后台转换为 SystemMessage 对象，并与 UserMessage 一起发送给大语言模型（LLM）
     *
     * @param memoryId    聊天id
     * @param userMessage 用户消息
     * @return
     */
    //系统消息提示词
    @SystemMessage("你是我的好朋友，请用四川话回答问题。")
    String chatSys(@MemoryId int memoryId, @UserMessage String userMessage);

    /**
     * 分离聊天记录
     * -- @SystemMessage 设定角色，塑造AI助手的专业身份，明确助手的能力范围
     * -- @SystemMessage 的内容将在后台转换为 SystemMessage 对象，并与 UserMessage 一起发送给大语言模型（LLM）
     *
     * @param memoryId    聊天id
     * @param userMessage 用户消息
     * @return
     */
    //系统消息提示词，从资源中加载提示模板
    @SystemMessage(fromResource = "my-prompt-template.txt")
    String chatSysText(@MemoryId int memoryId, @UserMessage String userMessage);

    /**
     * 分离聊天记录
     * -- @UserMessage：获取用户输入
     *
     * @param memoryId    聊天id
     * @param userMessage 用户消息
     * @return
     */
    //{{it}}表示这里唯一的参数的占位符
    @UserMessage("你是我的好朋友，请用四川话回答问题，并且添加一些表情符号。 {{it}}")
    String chatUser(@MemoryId int memoryId, @UserMessage String userMessage);

    /**
     * -- @V 明确指定传递的参数名称
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    @UserMessage("你是我的好朋友，请用粤语回答问题。{{message}}")
    String chatV2(@MemoryId int memoryId, @V("message") String userMessage);


    /**
     * -- @V 明确指定传递的参数名称
     *
     * @param memoryId
     * @param userMessage
     * @param username
     * @param age
     * @return
     */
    @SystemMessage(fromResource = "my-prompt-template3.txt")
    String chatV3(@MemoryId int memoryId, @UserMessage String userMessage, @V("username") String username, @V("age") int age);

    /**
     * 流式输出
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    Flux<String> chatFlux(@MemoryId int memoryId, @UserMessage String userMessage);
}
