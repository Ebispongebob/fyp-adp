package com.fyp.adp.dispatcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fyp.adp.basedata.rule.entity.EventRecord;
import com.fyp.adp.basedata.rule.service.EventService;
import com.fyp.adp.common.utils.DateUtils;
import com.fyp.adp.common.utils.UUIDUtil;
import com.fyp.adp.dispatcher.entity.CommonMessage;
import com.fyp.adp.dispatcher.entity.DispatcherMessage;
import com.fyp.adp.dispatcher.entity.SinkPattern;
import com.fyp.adp.dispatcher.enums.SendWay;
import com.fyp.adp.dispatcher.kafka.KafkaConsumerFactory;
import com.fyp.adp.dispatcher.sender.EmailSender;
import com.fyp.adp.dispatcher.sender.LarkBotSender;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;

@Component
public class AdpDispatchConsumer {

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
            KafkaConsumer<String, String> consumer = KafkaConsumerFactory.getConsumer("dispatcher_group", extraProp);
            consumer.subscribe(Lists.newArrayList("adp_dispatcher"));
            while (true) {
                // 每100ms poll
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                try {
                    for (ConsumerRecord<String, String> record : records) {
                        try {
                            // pull
                            DispatcherMessage message = JSON.parseObject(record.value(), DispatcherMessage.class);
                            // send
                            handle(message);
                            // save
                            afterHandle(message);
                        } catch (Throwable t) {
                            logger.error("handle adp dispatch consumer error ,offset:{},value:{}", record.offset(), record.value(), t);
                        }
                    }
                } catch (Throwable throwable) {
                    logger.error("handle adp dispatch consumer error", throwable);
                }
            }
        });
    }

    private void handle(DispatcherMessage message) {
        if (CollectionUtils.isNotEmpty(message.getBegin())) {
            SinkPattern   sinkPattern   = message.getBegin().get(0);
            CommonMessage commonMessage = new CommonMessage();
            commonMessage.setTitle("[" + sinkPattern.getReferenceId() + "] " + sinkPattern.getEventType());
            commonMessage.setReference(sinkPattern.getReferenceId());
            commonMessage.setEventTime(DateUtils.timestamp2format(sinkPattern.getEventTime()));
            JSONObject jsonObject = JSONObject.parseObject(sinkPattern.getRuleConfig());
            commonMessage.setMessage(jsonObject.getString("msg"));

            String way = jsonObject.getString("way");
            String url = jsonObject.getString("addr");
            switch (SendWay.getWay(way)) {
            case BOT -> new LarkBotSender(url, commonMessage).send();
            case EMAIL -> new EmailSender(url, commonMessage).send();
            }
        }
    }

    private void afterHandle(DispatcherMessage message) {
        for (SinkPattern sinkPattern : message.getBegin()) {
            EventRecord record = new EventRecord();
            record.setId(UUIDUtil.randomUUID().substring(0, 10));
            record.setEventTime(DateUtils.getDate(sinkPattern.getEventTime()));
            record.setEventType(sinkPattern.getEventType());
            record.setReferenceId(sinkPattern.getReferenceId());
            record.setReceivedTime(DateUtils.getDate(sinkPattern.getReceivedTime()));
            record.setSinkTime(new Date());
            eventService.saveRecord(record);
        }
    }
}
