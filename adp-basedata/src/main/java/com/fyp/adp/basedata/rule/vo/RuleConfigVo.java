package com.fyp.adp.basedata.rule.vo;

import lombok.Data;

@Data
public class RuleConfigVo {
    private String  ruleName;
    private String  eventType;
    private Integer windowSize;
    private Integer threshold;
    private String  condition;
    private String  alertConfig;
    private String  createTime;
}
