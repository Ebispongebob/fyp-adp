package com.fyp.adp.dispatcher.sender;

import com.fyp.adp.common.utils.HttpClientUtils;
import com.fyp.adp.dispatcher.constant.LarkTemplate;
import com.fyp.adp.dispatcher.constant.PlaceholderConstants;
import com.fyp.adp.dispatcher.entity.CommonMessage;
import com.google.common.collect.Maps;

import java.util.HashMap;

public class LarkBotSender implements Sender {
    HashMap<String, String> message = Maps.newHashMap();
    String                  url;

    public LarkBotSender(String url, CommonMessage commonMessage) {
        this.url = url;
        init(commonMessage);
    }

    private void init(CommonMessage commonMessage) {
        message.put("msg_type", "text");
        message.put("content", buildContent(commonMessage));
    }

    private String buildContent(CommonMessage commonMessage) {
        return LarkTemplate.botTextTemplate
                .replaceAll(PlaceholderConstants.message, commonMessage.getMessage())
                .replaceAll(PlaceholderConstants.title, commonMessage.getTitle())
                .replaceAll(PlaceholderConstants.reference, commonMessage.getReference())
                .replaceAll(PlaceholderConstants.eventTime, commonMessage.getEventTime());
    }

    @Override
    public void send() {
        HttpClientUtils.doGet(this.url, this.message);
    }
}
