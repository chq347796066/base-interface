package com.spring.iot.controller;

import com.spring.iot.kafka.KafkaSendService;
import com.spring.iot.start.OnMessageCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller
 * @author zx
 */
@Controller
@RequestMapping("/api/kafka/")
public class KafkaController {
    @Autowired
    private KafkaSendService kafkaSendService;

    @Autowired
    private OnMessageCallback onMessageCallback;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @GetMapping("send")
    @ResponseBody
    public boolean send(@RequestParam String message) {
        try {
            kafkaTemplate.send("testTopic", message);
            System.out.println("消息发送成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    @GetMapping("test")
    @ResponseBody
    public String test(){
        System.out.println("hello world!");
        return "ok";
    }
}