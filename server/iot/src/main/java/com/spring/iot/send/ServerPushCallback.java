package com.spring.iot.send;

import com.spring.iot.kafka.KafkaSendService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/20 17:58
 */
@Component(value = "serverPushCallback")
public class ServerPushCallback implements MqttCallback {
    private final static Logger logger = LoggerFactory.getLogger(ServerPushCallback.class);

    @Resource(name = "serverMqtt")
    private ServerMqtt serverMqtt;


    /**
     * 连接断开时的回调
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("连接断开，正常尝试重连 -> ", throwable);
        serverMqtt.startReconnect();
    }

    /**
     * 接收消息时的回调
     *
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("接收消息主题111111: " + topic);
        logger.info("接收消息Qos111111111: " + message.getQos());
        logger.info("接收消息内容111111111111: " + new String(message.getPayload()));
    }

    /**
     * 消息发送成功时的回调
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        logger.info("消息发送成功 -> ", token.isComplete());
    }

}
