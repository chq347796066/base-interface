package com.spring.iot.start;

import com.alibaba.fastjson.JSON;
import com.spring.iot.kafka.KafkaSendService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/20 14:45
 */
@Component
public class OnMessageCallback implements MqttCallback {
    private static KafkaSendService kafkaSendService;

    @Autowired
    public void KafkaSendService(KafkaSendService kafkaSendService) {
        OnMessageCallback.kafkaSendService = kafkaSendService;
    }

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        String msg = new String(message.getPayload());
        System.out.println("EMQ接收消息主题:" + topic);
        System.out.println("EMQ接收消息Qos:" + message.getQos());
        System.out.println("EMQ接收消息内容:" + msg);
        try {
            kafkaSendService.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("消息发送kafka:" + msg);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
