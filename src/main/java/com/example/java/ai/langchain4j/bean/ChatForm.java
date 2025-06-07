package com.example.java.ai.langchain4j.bean;

import lombok.Data;

@Data
public class ChatForm {
    private int memoryId;//对话id

    private String message;//用户问题

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}