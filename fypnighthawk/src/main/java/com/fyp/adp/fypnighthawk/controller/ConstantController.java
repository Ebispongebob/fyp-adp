package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.common.ro.Result;
import com.fyp.adp.common.utils.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/constant")
@CrossOrigin(allowedHeaders = "FYP-AUTH-TOKEN")
public class ConstantController {

    @GetMapping("/lastweek")
    public Result getPastWeek() {
        return Result.success(DateUtils.getLastWeekDates());
    }
}
