package com.fyp.adp.fypnighthawk.service;

import com.fyp.adp.basedata.rule.service.EventService;
import com.fyp.adp.fypnighthawk.AdpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdpApplication.class)
public class EventServiceTest {

    @Autowired
    EventService eventService;

    @Test
    public void testGroup() {
        System.out.println(eventService.getRankList());
    }

}
