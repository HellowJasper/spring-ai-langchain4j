package com.example.java.ai.langchain4j.bean;

import lombok.Data;

@Data
public class ChatForm {

    private String memoryId;//对话id
    private String message;//用户问题
    
}