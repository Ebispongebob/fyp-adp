package com.fyp.adp.basedata.rule.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "rule_reference_rel")
public class RuleReferenceRel {
    @Column(name = "reference_id")
    String referenceId;
    @Column(name = "rule_name")
    String ruleName;
}
