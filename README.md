# 自学langchain4j的一些小test
* 计划在一周之内完结这个小项目
* 后续会写一些我的个人笔记（可能会鸽）

## 1.1：首先创建一个Maven项目

## 1.2：导入依赖 这是最终需要的所有的依赖
~~~
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>spring-ai-langchain4j</artifactId>
    <version>1.0-SNAPSHOT</version>

<!--    指定的版本-->
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring-boot.version>3.2.6</spring-boot.version>
        <knife4j.version>4.3.0</knife4j.version>
        <langchain4j.version>1.0.0-beta3</langchain4j.version>
        <mybatis-plus.version>3.5.11</mybatis-plus.version>
    </properties>

    <dependencies>
        <!-- web应用程序核心依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 编写和运行测试用例 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- 前后端分离中的后端接口测试工具 -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
            <version>${knife4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

<!--        引入langchain4j-->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-open-ai-spring-boot-starter</artifactId>
        </dependency>

        <!-- 接入ollama -->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-ollama-spring-boot-starter</artifactId>
        </dependency>

        <!-- 接入阿里云百炼平台 -->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-community-dashscope-spring-boot-starter</artifactId>
        </dependency>

        <!--langchain4j高级功能-->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-spring-boot-starter</artifactId>
        </dependency>

        <!-- Spring Boot Starter Data MongoDB -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>

        <!-- Mysql Connector -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <!--mybatis-plus 持久层-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>

        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-document-parser-apache-pdfbox</artifactId>
        </dependency>

        <!--简单的rag实现-->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-easy-rag</artifactId>
        </dependency>
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-pinecone</artifactId>
            <version>1.0.1-beta6</version>
        </dependency>
        <!--流式输出-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-reactor</artifactId>
        </dependency>

    </dependencies>

    <dependencyManagement>
        <dependencies>
            <!--引入SpringBoot依赖管理清单-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

                <!--引入langchain4j依赖管理清单-->
                <dependency>
                    <groupId>dev.langchain4j</groupId>
                    <artifactId>langchain4j-bom</artifactId>
                    <version>${langchain4j.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>

            <!--引入百炼依赖管理清单-->
            <dependency>
                <groupId>dev.langchain4j</groupId>
                <artifactId>langchain4j-community-bom</artifactId>
                <version>${langchain4j.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>

    </dependencyManagement>

</project>
~~~
## 1.3：创建好，我们的第一个启动类
~~~
package com.example.java.ai.langchain4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class XiaoZhiApp {
    public static void main(String[] args) {
         SpringApplication.run(XiaoZhiApp.class, args);
    }
}
~~~
## 1.4：创建好启动类，我们就可以访问 http://localhost:8080/doc.html 来查看api文档

## 2.1 开始接入我们需要的大模型
本项目主要是学习的尚硅谷的小智医疗，主要使用的是LangChain4j
LangChain4j的库结构
*langchain4j-core 模块，定义了需要的API，我们可以根据API来完成聊天等功能
*主 langchain4j 模块，包含有用的工具，如文档加载器、聊天记忆实现，以及诸如人工智能服务等
高层功能。
*这是我个人认为LangChain4j比较有特色的地方，每个模块都将各种大语言模型提供商和嵌入存储集成到
LangChain4j 中。
## 2.2 添加依赖
~~~
<dependencies>
<!-- 基于open-ai的langchain4j接口：ChatGPT、deepseek都是open-ai标准下的大模型 -->
  <dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-open-ai</artifactId>
  </dependency>
</dependencies>
<dependencyManagement>
  <dependencies>
    <!--引入langchain4j依赖管理清单-->
      <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-bom</artifactId>
        <version>${langchain4j.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
  </dependencies>
</dependencyManagement>
~~~

## 2.2：创建测试用例
本次测试使用的是LangChain4j提供的演示密钥，使用的是gpt-40-mini  目前是2025年6月7日
~~~
@SpringBootTest
public class LLMTest {
    @Test
    public void testGPTDemo() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();


        String answer = model.chat("你是谁");
        System.out.println(answer);
    }
~~~
## 3.1:SpringBoot整合
这一步将 `langchain4j-open-ai` 替换成`langchain4j-open-ai-spring-boot-starter`
~~~
<dependency>
  <groupId>dev.langchain4j</groupId>
  <artifactId>langchain4j-open-ai-spring-boot-starter</artifactId>
</dependency>
~~~
## 3.2:配置模型参数
~~~
#langchain4j测试模型
langchain4j.open-ai.chat-model.api-key=demo
langchain4j.open-ai.chat-model.model-name=gpt-4o
#请求和响应日志
langchain4j.open-ai.chat-model.log-requests=true
langchain4j.open-ai.chat-model.log-responses=true
#启用日志debug级别
logging.level.root=debug
~~~
## 3.3：创建测试用例
~~~
@Autowired
    private OpenAiChatModel  openAiChatModel;

    @Test
    public void testSpringBoot() {
        String answer = openAiChatModel.chat("你是谁");
        System.out.println(answer);
    }
~~~
