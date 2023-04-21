package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.rule.service.EventService;
import com.fyp.adp.common.ro.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rule")
@CrossOrigin(allowedHeaders = "FYP-AUTH-TOKEN")
public class RuleController {

    @Autowired
    EventService eventService;

    @GetMapping("/event/list")
    public Result getRecordList() {
        return Result.success(eventService.get10Records());
    }

    @GetMapping("event/rank")
    public Result getRankList() {
        return Result.success(eventService.getRankList());
    }
}
