package com.fyp.adp.basedata.rule.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class EventRateListVo {
    @Column(name = "event_type")
    private String event_type;

    @Column(name = "rate")
    private Double rate;
}
