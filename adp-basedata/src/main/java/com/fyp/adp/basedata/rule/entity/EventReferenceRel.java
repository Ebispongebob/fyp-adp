package com.fyp.adp.basedata.rule.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "event_reference_rel")
public class EventReferenceRel {
    @Column(name = "reference_id")
    String ReferenceId;
    @Column(name = "event_type")
    String EventType;
}
