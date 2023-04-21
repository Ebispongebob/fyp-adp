package com.fyp.adp.basedata.rule.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "event_record")
public class EventRecord {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "reference_id")
    private String referenceId;
    @Column(name = "event_type")
    private String eventType;
    @Column(name = "event_time")
    private Date   eventTime;
    @Column(name = "received_time")
    private Date   receivedTime;
    @Column(name = "sink_time")
    private Date   sinkTime;
}
