package com.fyp.adp.basedata.rule.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RankListVo {
    @Column(name = "reference_id")
    private String reference_id;

    @Column(name = "count")
    private Long count;
}
