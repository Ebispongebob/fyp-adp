package com.fyp.adp.basedata.rule.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ReferenceVo {
    private String referenceId;
    private String description;
    private String brokerAddr;
    private String topic;
    private String createTime;
}
