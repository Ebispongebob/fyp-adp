package com.fyp.adp.basedata.rule.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "adp_reference")
public class Reference {
    @Id
    @Column(name = "id")
    private Long    id;
    @Column(name = "reference_id")
    private String  referenceId;
    @Column(name = "description")
    private String  description;
    @Column(name = "broker_addr")
    private String  brokerAddr;
    @Column(name = "topic")
    private String  topic;
    @Column(name = "create_time")
    private Date    createTime;
    @Column(name = "enable")
    private Boolean enable;
}
