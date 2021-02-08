package com.spring.iotdata.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/21 14:22
 */
@Slf4j
@Component
public class KafkaReceiver {
    @KafkaListener(topics = {"kafka-test"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();

            log.info("kafka 接收消息 record =" + record);
            log.info("kafka 接收消息 message =" + message);
        }

    }

}

