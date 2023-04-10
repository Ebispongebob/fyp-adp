package com.fyp.adp.rule.engine.converter;

import com.fyp.adp.common.utils.UUIDUtil;
import com.fyp.adp.rule.engine.message.EventMessage;
import com.fyp.adp.rule.engine.message.ReceivedMessage;
import org.apache.flink.api.common.functions.MapFunction;

public class EventMessageCompensator implements MapFunction<EventMessage, ReceivedMessage> {
    @Override
    public ReceivedMessage map(EventMessage eventMessage) throws Exception {
        ReceivedMessage receivedMessage = new ReceivedMessage();
        receivedMessage.setId(eventMessage.getId());
        receivedMessage.setUid(UUIDUtil.randomUUID());
        receivedMessage.setReferenceId(eventMessage.getReferenceId());
        receivedMessage.setEventTime(eventMessage.getEventTime());
        receivedMessage.setEventType(eventMessage.getEventType());
        receivedMessage.setReceivedTime(System.currentTimeMillis());
        return receivedMessage;
    }
}
