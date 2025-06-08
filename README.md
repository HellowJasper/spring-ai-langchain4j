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
为每个用户的新聊天或者不同的用户区分聊天回忆
### 8.3.1 创建记忆隔离对话智能体
~~~
@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculatorTools"
)
public interface SeparateChatAssistant {
    /**
     * 分离聊天记录
     * @param memoryId 聊天id
     * @param userMessage 用户消息
     * @return
     */
   
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
~~~

### 8.3.2 配置ChatMemoryProvider
~~~
@Configuration
public class SeparateChatAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {

        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }
}
~~~
### 8.3.3 测试对话助手
~~~
 @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory5() {
        
        //调用service的接口
        String answer1 = separateChatAssistant.chat(1,"我是张三");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer2);
        String answer3 = separateChatAssistant.chat(2,"我是谁");
        System.out.println(answer3);

    }
~~~

# 持久化聊天记忆Persistence
本项目使用MongoDB来作为存储介质

NoSql数据库 文档型数据库，数据以 JSON - like 的文档形式存储，具有高度的灵活性和可扩展性。它
不需要预先定义严格的表结构，适合存储半结构化或非结构化的数据。

适用于消息信息多样化，如有文本消息，图片，语音等多媒体数据

## 9.1 安装MongoDB
服务器： mongodb-windows-x86_64-8.0.6-signed.msi https://www.mongodb.com/try/download/co
mmunity

命令行客户端 ： mongosh-2.5.0-win32-x64.zip https://www.mongodb.com/try/download/shell

图形客户端： mongodb-compass-1.39.3-win32-x64.exe https://www.mongodb.com/try/download/c
ompass

## 9.2 MongoBB的使用
启动 MongoDB Shell：

在命令行中输入 mongosh 命令，启动 MongoDB Shell，如果 MongoDB 服务器运行在本地默认端口
（27017），则可以直接连接。

连接到 MongoDB 服务器：

如果 MongoDB 服务器运行在非默认端口或者远程服务器上，可以使用以下命令连接：

其中 <hostname> 是 MongoDB 服务器的主机名或 IP 地址， <port> 是 MongoDB 服务器的端口号。

执行基本操作：

连接成功后，可以执行各种 MongoDB 数据库操作。例如：

* 查看当前数据库： db
* 显示数据库列表： show dbs
* 切换到指定数据库： use <database_name>
* 执行查询操作： db.<collection_name>.find()
* 插入文档： db.<collection_name>.insertOne({ ... })
* 更新文档： db.<collection_name>.updateOne({ ... })
* 删除文档： db.<collection_name>.deleteOne({ ... })
* 退出 MongoDB Shell： quit() 或者 exit


## 9.3 引入MongoDB依赖
~~~
 <!-- Spring Boot Starter Data MongoDB -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
~~~
连接配置
~~~
#MongoDB连接配置
spring.data.mongodb.uri=mongodb://localhost:27017/chat_memory_db
~~~

## 9.4 CRUD测试
我们需要创建一个实体类来进行余MangoDB中的文档进行映射
~~~
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
//创建测试类：
public class ChatMessages {

    //唯一标识，映射到 MongoDB 文档的 _id 字段
    @Id
    private ObjectId id;
    // private int messageId;
    private String content; //存储当前聊天记录列表的json字符串
}
~~~

创建对应的测试类
~~~
@SpringBootTest
public class MongoCrudTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档
     */
//    @Test
//    public void testInsert() {
//        mongoTemplate.insert(new ChatMessages(1L, "聊天记录"));
//    }

    /**
     * 插入文档
     */
    @Test
    public void testInsert2() {
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("聊天记录");
        mongoTemplate.insert(chatMessages);

    }
    @Test
    public void testFindByID(){
        ChatMessages chatMessages = mongoTemplate.findById("68418e5d4ce2830674ec231a", ChatMessages.class);
        System.out.println(chatMessages);
    }

    @Test
    public void testUpdate(){
        Criteria  criteria = Criteria.where("_Id").is("100");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "新的聊天记录");
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }
    @Test
    public void testDelete(){
        Criteria  criteria = Criteria.where("_Id").is("100");
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}
~~~
# 持久化聊天

## 10.1 优化实体类
~~~
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
//创建测试类：
public class ChatMessages {

    //唯一标识，映射到 MongoDB 文档的 _id 字段
    @Id
    private ObjectId id;
    private int messageId;
    private String content; //存储当前聊天记录列表的json字符串
}
~~~
## 10.2 创建持久化类
~~~
@Component
public class MongoChatMemoryStore implements ChatMemoryStore {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);

        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        if(chatMessages == null)
            return new LinkedList<>();
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
        //根据query条件能查询出文档，则修改文档；否则新增文档
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }
    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}
~~~
在SeparateChatAssistantConfig中，添加MongoChatMemoryStore对象的配置
~~~
@Configuration
public class SeparateChatAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {

        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }
}
~~~

## 10.3 测试
正常测试，如果通过的话会显示在chat_memory_db.chat_messages中

# 提示词Prompt
个人理解为就是你对大模型的设定，比如将与你对话的大模型设定成二次元老婆之类
~~~
@SystemMessage("你是我二次元老婆，请回答我相关问题。")//系统消息提示词
String chat(@MemoryId int memoryId, @UserMessage String userMessage);
~~~
运行逻辑：

`SystemMessage`的内容在后台转换为`SystemMessage`对象 一并发给LLM(大语言模型) 且该内容只会发送一次

如果要显示今天的日期，我们需要在提示词中添加当前日期的占位符`{{current_date}}`

## 11.1 从资源文件中加载提示模板
@SystemMessage 注解还可以从资源中加载提示模板

示例

~~~
@SystemMessage(fromResource = "my-prompt-template.txt")
String chat(@MemoryId int memoryId, @UserMessage String userMessage);
~~~

## 11.2 用户提示词模板使用

`@UserMessage`：获取用户的输入

~~~
@UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。 {{it}}") //{{it}}表示这里唯一的参数的占位符
String chat(String message);
~~~

## 12.3 指定参数名称
### 12.3.1 @V的使用
`@V` 明确指定传递的参数名称
~~~
@UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。{{message}}")
String chat(@V("message") String userMessage);
~~~

### 12.3.2 多个参数的情况使用
如果有两个或两个以上的参数，我们必须要用`@V` ，在 `SeparateChatAssistant` 中定义方法 chat2

~~~
@UserMessage("你是我的好朋友，请用粤语回答问题。{{message}}")
String chat2(@MemoryId int memoryId, @V("message") String userMessage);
~~~

### 12.3.3@SystemMessage和@V的使用
也可以将`@SystemMessage` 和 `@V` 结合使用
在 SeparateChatAssistant 中添加方法chat3
~~~
@SystemMessage(fromResource = "my-prompt-template3.txt")
String chat3(
         @MemoryId int memoryId,
         @UserMessage String userMessage,
         @V("username") String username,
         @V("age") int age
);
~~~
# 硅谷小智的创建与实现
