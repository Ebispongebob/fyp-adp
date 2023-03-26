package com.fyp.adp.basedata.rule;

import com.fyp.adp.basedata.event.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarningRule implements Rule {
    private Long          id;
    private String        ruleName;
    private String        eventType;
    private Long          eventId;
    private Integer       windowSize;
    private Integer       threshold;
    private RuleCondition condition;
    private String        alertMessage;
    private EventStatus   status;
    private Date          createTime;
    private Date          updateTime;
}
