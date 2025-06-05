package com.example.java.ai.langchain4j;

import com.example.java.ai.langchain4j.assistant.MemoryChatAssistant;
import com.example.java.ai.langchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {

@Autowired
private SeparateChatAssistant separateChatAssistant;
@Test
public void testSystemMessage() {

    String answer = separateChatAssistant.chat(3, "今天是几号");
    System.out.println(answer);
}
    @Autowired
    private MemoryChatAssistant memoryChatAssistant;
    @Test
    public void testUserMessage() {
        String answer = memoryChatAssistant.chat("这是河南话？");
        System.out.println(answer);
    }

    @Test
    public void testV() {
        String answer1 = memoryChatAssistant.chat2(1, "我是老贾");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat2(1, "我是谁");
        System.out.println(answer2);
    }

}