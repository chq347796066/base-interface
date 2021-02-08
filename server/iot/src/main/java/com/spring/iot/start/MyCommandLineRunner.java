package com.spring.iot.start;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:应用启动订阅消息
 * @return:
 * @author: 赵进华
 * @time: 2020/7/21 10:55
 */
@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {
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


	@Override
	public void run(String... args) {

		String subTopic = "zjh-topic";
		String content = "Hello World";
		int qos = 2;
		String broker = "tcp://10.18.20.62:1883";
		String clientId = "emqx_test";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient client = new MqttClient(broker, clientId, persistence);

			// MQTT 连接选项
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName("admin");
			connOpts.setPassword("123456".toCharArray());
			// 保留会话
			connOpts.setCleanSession(true);

			// 设置回调
			client.setCallback(new OnMessageCallback());

			// 建立连接
			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);

			System.out.println("Connected");
			System.out.println("Publishing message: " + content);

			// 订阅
			client.subscribe(subTopic);

		} catch (MqttException me) {
			me.printStackTrace();
		}

	}

}
