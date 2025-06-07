package com.example.java.ai.langchain4j.controller;

import com.example.java.ai.langchain4j.assistant.XiaozhiAgent;
import com.example.java.ai.langchain4j.bean.ChatForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.stream.Collectors;

@Tag(name = "小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {

    @Autowired
    private XiaozhiAgent xiaozhiAgent;

    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(HttpServletRequest request) throws IOException, IOException {
        // 1. 手动读取原始请求体
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        System.out.println("Raw Request Body: " + requestBody);

        // 2. 手动解析JSON
        ObjectMapper mapper = new ObjectMapper();
        ChatForm chatForm;
        try {
            chatForm = mapper.readValue(requestBody, ChatForm.class);
        } catch (JsonProcessingException e) {
            return Flux.just("ERROR: 无效的JSON格式");
        }

        // 3. 验证数据
        if (chatForm.getMessage() == null) {
            return Flux.just("ERROR: 消息内容不能为空");
        }

        System.out.println("Parsed Data - memoryId: " + chatForm.getMemoryId() +
                ", message: " + chatForm.getMessage());

        return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
