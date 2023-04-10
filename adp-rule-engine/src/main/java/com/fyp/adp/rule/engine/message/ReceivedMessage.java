package com.fyp.adp.rule.engine.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceivedMessage extends EventMessage{
    private String uid;
    private Long receivedTime;
}
