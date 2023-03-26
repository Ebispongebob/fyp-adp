package com.fyp.adp.job.adp.instantx.schema;

import com.alibaba.fastjson.JSON;
import com.fyp.adp.basedata.event.Event;
import com.fyp.adp.common.utils.JsonUtils;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.nio.charset.StandardCharsets;

public class EventSchema implements SerializationSchema<Event>, KafkaDeserializationSchema<Event> {

    @Override
    public byte[] serialize(Event element) {
        return JsonUtils.toJsonString(element).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean isEndOfStream(Event nextElement) {
        return false;
    }

    @Override
    public Event deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
        String value = new String(record.value(), StandardCharsets.UTF_8);
        return JSON.parseObject(value, Event.class);
    }

    @Override
    public TypeInformation<Event> getProducedType() {
        return TypeInformation.of(new TypeHint<Event>() {
        });
    }
}