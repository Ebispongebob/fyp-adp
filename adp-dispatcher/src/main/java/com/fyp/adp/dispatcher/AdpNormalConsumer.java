package com.fyp.adp.dispatcher;

import com.alibaba.fastjson.JSON;
import com.fyp.adp.basedata.rule.entity.EventRecord;
import com.fyp.adp.basedata.rule.service.EventService;
import com.fyp.adp.common.utils.UUIDUtil;
import com.fyp.adp.dispatcher.kafka.KafkaConsumerFactory;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Executors;

@Component
public class AdpNormalConsumer {

    @Autowired
    EventService eventService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        // 消费线程
        startConsumer();
    }

    private void startConsumer() {
        Executors.newFixedThreadPool(1).execute(() -> {
            Properties extraProp = new Properties();
            // 一次只拉1条
            extraProp.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
            KafkaConsumer<String, String> consumer = KafkaConsumerFactory.getConsumer("dispatcher_group_02", extraProp);
            consumer.subscribe(Lists.newArrayList("adp_normal_topic"));
            while (true) {
                // 每100ms poll
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                try {
                    for (ConsumerRecord<String, String> record : records) {
                        try {
                            // pull
                            EventRecord message = JSON.parseObject(record.value(), EventRecord.class);
                            System.out.println("----" + message);
                            // save
                            afterHandle(message);
                        } catch (Throwable t) {
                            logger.error("handle adp normal consumer error ,offset:{},value:{}", record.offset(), record.value(), t);
                        }
                    }
                } catch (Throwable throwable) {
                    logger.error("handle adp normal consumer error", throwable);
                }
            }
        });
    }

    private void afterHandle(EventRecord message) {
        message.setId(UUIDUtil.randomUUID());
        message.setReceivedTime(message.getEventTime());
        message.setSinkTime(message.getEventTime());
        eventService.saveRecord(message);
    }
}
