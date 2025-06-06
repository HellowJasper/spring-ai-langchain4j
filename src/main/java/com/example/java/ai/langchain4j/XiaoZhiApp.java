package com.example.java.ai.langchain4j;

import com.example.java.ai.langchain4j.bean.ChatForm;
import com.example.java.ai.langchain4j.controller.XiaozhiController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class XiaoZhiApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(XiaoZhiApp.class, args);

        XiaozhiController controller = context.getBean(XiaozhiController.class);

        ChatForm form = new ChatForm();
        form.setMemoryId("1");
        form.setMessage("你叫什么名字");

        String result = controller.chat(form);
        System.out.println("Response: " + result);
    }
    }
