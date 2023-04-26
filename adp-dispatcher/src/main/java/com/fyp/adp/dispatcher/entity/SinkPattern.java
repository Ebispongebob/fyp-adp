package com.fyp.adp.dispatcher.entity;

import lombok.Data;

@Data
public class SinkPattern {
    private Long eventTime;
    private String eventType;
    private int id;
    private long receivedTime;
    private String referenceId;
    private String ruleConfig;
    private String uid;
}
