package com.spring.iot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/21 14:22
 */
@Slf4j
@Component
public class KafkaReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    @KafkaListener(topics = "testTopic")
    public void onMessage(String message){
        System.out.println("kafka接收成功 message: " + message);
        IOTMsg msg=new IOTMsg();
        msg.setMsg(message);
        msg.setCreateDate(new Date());
        mongoTemplate.save(msg);
        System.out.println("MongDB保存消息成功 message: " + message);
    }

}

