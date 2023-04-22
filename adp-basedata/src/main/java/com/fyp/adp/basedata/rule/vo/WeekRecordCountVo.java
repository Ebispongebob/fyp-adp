package com.fyp.adp.basedata.rule.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class WeekRecordCountVo {
    @Column(name = "date")
    String date;
    @Column(name = "count")
    Long count;
}
