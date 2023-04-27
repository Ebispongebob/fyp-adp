package com.fyp.adp.dispatcher.entity;

import lombok.Data;

@Data
public class CommonMessage {
    private String message;
    private String title;
    private String reference;
    private String eventTime;
}
