package com.fyp.adp.basedata.rule.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "adp_rule")
public class RuleConfig {
    @Id
    @Column(name = "id")
    private Integer    id;
    @Column(name = "rule_name")
    private String  ruleName;
    @Column(name = "event_type")
    private String  eventType;
    @Column(name = "window_size")
    private Integer windowSize;
    @Column(name = "threshold")
    private Integer threshold;
    @Column(name = "`condition`")
    private String  condition;
    @Column(name = "alert_config")
    private String  alertConfig;
    @Column(name = "create_time")
    private Date    createTime;
}
