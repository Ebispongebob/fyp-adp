package com.fyp.adp.basedata.rule.service;

import com.fyp.adp.basedata.rule.entity.EventReferenceRel;
import com.fyp.adp.basedata.rule.mapper.EventReferenceRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    EventReferenceRelMapper eventReferenceRelMapper;

    public Integer addEvent(EventReferenceRel event) {
        return eventReferenceRelMapper.insert(event);
    }
}
