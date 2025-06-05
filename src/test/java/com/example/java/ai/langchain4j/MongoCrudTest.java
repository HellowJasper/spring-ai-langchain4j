package com.example.java.ai.langchain4j;

import com.example.java.ai.langchain4j.bean.ChatMessages;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


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

