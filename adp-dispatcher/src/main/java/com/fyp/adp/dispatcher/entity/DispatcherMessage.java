package com.fyp.adp.dispatcher.entity;

import lombok.Data;

import java.util.List;

@Data
public class DispatcherMessage {
    private List<SinkPattern> begin;
}
