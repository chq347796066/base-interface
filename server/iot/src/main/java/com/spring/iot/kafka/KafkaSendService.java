package com.spring.iot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/22 16:00
 */
@Slf4j
@Component
public class KafkaSendService {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public boolean send(String msg) {
        try {
            kafkaTemplate.send("testTopic", msg);
            System.out.println("kafka 消息发送成功"+msg);
        } catch (Exception e) {
        }
        return true;
    }
}
