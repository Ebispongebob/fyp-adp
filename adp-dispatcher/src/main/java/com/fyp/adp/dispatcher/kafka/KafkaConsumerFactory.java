package com.fyp.adp.dispatcher.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class KafkaConsumerFactory {

    /**
     * 获取一个kafka消费者
     * @param groupId 消费者id
     * @return 消费者
     */
    public static KafkaConsumer<String, String> getConsumer(String groupId) {
        return getConsumer(groupId, new Properties());
    }

    /**
     * 获取一个kafka消费者
     * @param groupId 消费者id
     * @param extraProp 额外配置
     * @return 消费者
     */
    public static KafkaConsumer<String, String> getConsumer(String groupId, Properties extraProp) {
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "adp.zoons.top:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.putAll(extraProp);
        return new KafkaConsumer<>(props);
    }
}
