package com.spring.iot.send;

import com.spring.common.util.random.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/20 17:57
 */
@Slf4j
@Component(value = "serverMqtt")
public class ServerMqtt {

    private static MqttClient client;
    private static MqttConnectOptions options;
    private final ConcurrentHashMap<String, MqttTopic> topicMap = new ConcurrentHashMap<>();

    @Value("${mqtt.host}")
    public String host;
    @Value("${mqtt.topic}")
    public String topic;
    @Value("${mqtt.subscribe.clientid}")
    public String clientId;
    @Value("${mqtt.username}")
    public String userName;
    @Value("${mqtt.password}")
    public String password;

    @Resource(name = "serverPushCallback")
    private ServerPushCallback serverPushCallback;

    @PostConstruct
    public void start() {
        try {
            // host: 服务器地址
            // clientid: 客户端ID(连接mqtt服务的唯一标识，用来区分不同的客户端)
            // MemoryPersistence: 设置clientid的保存形式，默认以内存保存
            client = new MqttClient(host, clientId, new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            // 设置是否清空session
            // true: 表示服务器会保留客户端的连接记录，false: 表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            options.setUserName(userName);
            options.setPassword(password.toCharArray());
            // 设置超时时间
            options.setConnectionTimeout(10);
            // 设置会话心跳时间
            options.setKeepAliveInterval(20);
            // 设置回调
           // client.setCallback(serverPushCallback);
            client.connect(options);
        } catch (Exception e) {
            log.error("mqtt启动失败 -> ", e);
        }
    }

    /**
     * 发布消息
     *
     * @param topic
     * @param payload
     */
    public void publish(String topic, byte[] payload) {
        try {
            MqttTopic mqttTopic = topicMap.get(topic);
            if (mqttTopic == null) {
                topicMap.putIfAbsent(topic, client.getTopic(topic));
            }
            mqttTopic = topicMap.get(topic);
            final MqttMessage message = new MqttMessage(payload);
            message.setQos(2);
            MqttDeliveryToken token = mqttTopic.publish(message);
            System.out.println("EMQ发布消息成功"+message);
  /*          token.waitForCompletion();
            if (token.isComplete()) {
                log.info("EMQ发布消息成功: message:{}", message);
            } else {
                log.info("EMQ发布消息失败: message:{}", message);
            }*/
        } catch (Exception e) {
            log.error("mqtt发布消息失败 -> ", e);
        }
    }

    /**
     * 重新连接
     */
    public synchronized void startReconnect() {
        if (!client.isConnected()) {
            while (!client.isConnected()) {
                log.info("mqtt开始尝试重连");
                try {
                    TimeUnit.SECONDS.sleep(2);
                    client.connect(options);
                    log.info("mqtt重连成功");
                    break;
                } catch (Exception e) {
                    log.error("mqtt重连失败，继续重连中");
                }
            }
        } else {
            log.info("mqtt已经连接，无需重连");
        }
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void publishData() {
        String message = "Hello World-"+ RandomUtil.getRandomNo();
        publish(topic, message.getBytes());
    }

}
