package com.example.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaozhi",
        tools = "appointmentTools" //tools配置
)
public interface XiaozhiAgent {

    @SystemMessage(fromResource = "Xiaozhi-prompt-template.txt")
    String chat(@MemoryId String memoryId,@UserMessage String userMessage);

}
