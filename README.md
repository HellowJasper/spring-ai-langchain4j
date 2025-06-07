# 自学langchain4j的一些小test
* 计划在一周之内完结这个小项目
* 后续会写一些我的个人笔记（可能会鸽）

# LangChain4j入门

## 1.1 首先创建一个Maven项目

## 1.2 导入依赖 这是最终需要的所有的依赖
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
## 1.3 创建好，我们的第一个启动类
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
## 1.4 创建好启动类，我们就可以访问 http://localhost:8080/doc.html 来查看api文档

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

## 2.2 创建测试用例
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
## 3.1 SpringBoot整合
这一步将 `langchain4j-open-ai` 替换成`langchain4j-open-ai-spring-boot-starter`
~~~
<dependency>
  <groupId>dev.langchain4j</groupId>
  <artifactId>langchain4j-open-ai-spring-boot-starter</artifactId>
</dependency>
~~~
## 3.2 配置模型参数
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
## 3.3 创建测试用例
~~~
@Autowired
    private OpenAiChatModel  openAiChatModel;

    @Test
    public void testSpringBoot() {
        String answer = openAiChatModel.chat("你是谁");
        System.out.println(answer);
    }
~~~

# 学习接入其他大模型
* LangChain4j支持的大模型详细文档信息* ：https://docs.langchain4j.dev/integrations/language-models/

## 4.1 接入deepseek
第一步：访问 https://www.deepseek.com/ 

第二步：注册账号 获取需要的base_url和api_key ,如果只想练习一下可以充值1元，够用了

## 4.2 配置开发参数
将获取的api_key 配置在环境变量中，环境变量的命名随意，我个人命名为`DEEP_SEEK_API_KEY`配置结束后需要重启一下IDEA，不然可能不生效
## 4.3 配置模型参数
~~~
#DeepSeek
langchain4j.open-ai.chat-model.base-url=https://api.deepseek.com
langchain4j.open-ai.chat-model.api-key=${DEEP_SEEK_API_KEY}
#DeepSeek-V3
langchain4j.open-ai.chat-model.model-name=deepseek-chat
#DeepSeek-R1 推理模型
#langchain4j.open-ai.chat-model.model-name=deepseek-reasoner
~~~

# ollama的本地部署
## 5.1 下载并安装ollama 注意下载会自动安装在C盘，如果C盘爆红的话，可以在别的盘
在D盘创建ollama文件夹，将文件包装进去
以管理员身份，打开CMD窗口
具体操作： win+r 输入cmd ctrl + shift + 回车
d：setx  OLLAMA_MODELS   d:\ollama  /M
切换到ollama目录，安装ollama程序的指定目录
cd  ollama
cls
ollamaSetup.exe   /dir=d:\ollama
在弹出的安装界面中，点 Install 后， 等待完成
最终 测试一下是否成功
ollama  -v
大家要是实在看不懂可以到：https://www.yuque.com/_h2o/opkle2/gfr9to0e6867tfbk?singleDoc# 看我请教的教程，`感谢开源`

## 5.2 引入依赖
~~~
<!-- 接入ollama -->
<dependency>
<groupId>dev.langchain4j</groupId>
<artifactId>langchain4j-ollama-spring-boot-starter</artifactId>
</dependency>
~~~
## 5.3 配置模型参数
~~~
#ollama
langchain4j.ollama.chat-model.base-url=http://localhost:11434
langchain4j.ollama.chat-model.model-name=deepseek-r1:1.5b
langchain4j.ollama.chat-model.log-requests=true
langchain4j.ollama.chat-model.log-responses=true
~~~

## 5.4 创建测试用例
~~~
 /**
     * ollama接入
     */
    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Test
    public void testOllama() {
        //向模型提问
        String answer = ollamaChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }
~~~

# 接入阿里百炼平台
## 6.1 阿里百炼
` 支持接入的模型列表` ： https://help.aliyun.com/zh/model-studio/models

` 模型广场` ： https://bailian.console.aliyun.com/?productCode=p_efm#/model-market

然后申请体验

申请apiKey： https://bailian.console.aliyun.com/?apiKey=1&productCode=p_efm#/api-key

## 6.2 配置apiKey

配置apiKey：配置在环境变量DASH_SCOPE_API_KEY中 具体操作如上

## 6.3 添加依赖
~~~
<!-- 接入阿里云百炼平台 -->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-community-dashscope-spring-boot-starter</artifactId>
        </dependency>

 <!--引入百炼依赖管理清单 写在dependencyManagement中-->
            <dependency>
                <groupId>dev.langchain4j</groupId>
                <artifactId>langchain4j-community-bom</artifactId>
                <version>${langchain4j.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
~~~

## 6.4 配置模型参数
~~~
#阿里百炼平台
langchain4j.community.dashscope.chat-model.api-key=${DASH_SCOPE_API_KEY}
langchain4j.community.dashscope.chat-model.model-name=qwen-max
~~~

## 6.5 测试
~~~
/**
     * 通义千问大模型
     */
    @Autowired
    private QwenChatModel qwenChatModel;
    @Test
    public void testDashScopeQwen() {
        //向模型提问
        String answer = qwenChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }
/**
    * 生成图形测试
    */

@Test
    public void testDashScopeWanx(){
        WanxImageModel wanxImageModel = WanxImageModel
                .builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(System.getenv("DASH_SCOPE_API_KEY"))
                .build();

        Response<Image> response = wanxImageModel.generate("奇幻森林精灵：在一片弥漫着轻柔薄雾的\n" +
                "古老森林深处，阳光透过茂密枝叶洒下金色光斑。一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她\n" +
                "有着海藻般的绿色长发，发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的\n" +
                "连衣裙，手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，蘑菇和蕨类植\n" +
                "物丛生，营造出神秘而梦幻的氛围。");
        URI url = response.content().url();
        System.out.println(url);

    }

~~~
## 6.6 测试deepseek
`集成配置`
~~~
#集成百炼-deepseek
langchain4j.open-ai.chat-model.base-url=https://dashscope.aliyuncs.com/compatiblemode/v1
langchain4j.open-ai.chat-model.api-key=${DASH_SCOPE_API_KEY}
langchain4j.open-ai.chat-model.model-name=deepseek-v3
#温度系数：取值范围通常在 0 到 1 之间。值越高，模型的输出越随机、富有创造性；
# 值越低，输出越确定、保守。这里设置为 0.9，意味着模型会有一定的随机性，生成的回复可能会比较多样化。
langchain4j.open-ai.chat-model.temperature=0.9
~~~
`测试可与使用之前的`

# 人工智能服务AiService  个人认为LangChain的核心
## 7.1 引入依赖
~~~
 <!--langchain4j高级功能-->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-spring-boot-starter</artifactId>
        </dependency>
~~~
## 7.2 创建接口
~~~
public interface Assistant {

    String chat(@UserMessage String userMessage);
}
~~~
## 7.3 测试用例
~~~
@SpringBootTest
public class AIServiceTest {
    @Autowired
    private QwenChatModel qwenChatModel;
    @Test
    public void testChat() {
        //创建AIService
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        //调用service的接口
        String answer = assistant.chat("你叫什么名字");
        System.out.println(answer);
    }
}
~~~
## 7.4 @AiService
~~~
@AiService(wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"
)
public interface Assistant {

    String chat(@UserMessage String userMessage);
}
~~~
## 7.5 测试
~~~
@Autowired
    private Assistant assistant;
    @Test
    public void testChat2() {
        String answer = assistant.chat("你叫什么名字");
        System.out.println(answer);
    }
~~~

AiServices 会把 Assistant 接口和相关组件组合在一起，并利用`反射机制`生成一个代理对象，这个对象实现了 Assistant 接口。
这个代理对象的作用是处理方法调用时的参数和返回值转换。也就是说，它会在背后自动完成`数据格式的转换`，让你可以更简单地使用接口来调用大模型。
以 chat 方法为例：
输入方面：你传入的是一个字符串，但大模型需要的是 UserMessage 类型的数据。代理对象会自动把这个字符串包装成 UserMessage 对象，再交给大模型处理。
输出方面：大模型返回的是 AiMessage 类型的结果，但你希望得到的是字符串。代理对象会从 AiMessage 中提取出内容，自动转成字符串再返回给你。


# 聊天记忆的实现 Chat memory
## 8.1 测试对话是否有记忆
很显然在没有配置的情况下一定是没有记忆的
## 8.2 使用AiService实现聊天记忆
### 8.2.1创建记忆对话智能体
当AiService由很多组件组成的时候，就可以称之为`智能体`了

如下：
~~~
@AiService(wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface MemoryChatAssistant {

    String chat(String message);

}
~~~

### 8.2.2 配置ChatMemory
~~~
@Configuration
public class MemoryChatAssistantConfig {
    @Bean
    public ChatMemory  chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }
}
~~~
### 8.2.3 测试
~~~
@Autowired
    private Assistant assistant;

    @Test
    public void testChatMemory() {
        String answer1 = assistant.chat("我是老贾");
        System.out.println(answer1);
        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }
~~~
## 8.3 隔离聊天记录
### 8.3.1 创建记忆隔离对话智能体
~~~

~~~

