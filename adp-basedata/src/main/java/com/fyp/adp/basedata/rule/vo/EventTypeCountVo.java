package com.fyp.adp.basedata.rule.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class EventTypeCountVo {
    @Column(name = "normal")
    Long normal;
    @Column(name = "warning")
    Long warning;
    @Column(name = "error")
    Long error;
}
