package com.fyp.adp.basedata.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Long        id;
    private String      eventType;
    private EventStatus status;
    private Long        timestamp;
}
