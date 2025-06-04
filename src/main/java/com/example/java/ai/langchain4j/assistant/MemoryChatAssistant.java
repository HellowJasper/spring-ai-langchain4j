package com.example.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import org.apache.commons.lang3.ClassUtils;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {

    String chat(String message);
}
