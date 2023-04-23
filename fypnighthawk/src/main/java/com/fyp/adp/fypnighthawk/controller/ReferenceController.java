package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.rule.service.ReferenceService;
import com.fyp.adp.common.ro.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/refer")
@CrossOrigin(allowedHeaders = "FYP-AUTH-TOKEN")
public class ReferenceController {

    @Autowired
    ReferenceService referenceService;

    @GetMapping("/get")
    public Result getAllReference() {
        return Result.success(referenceService.getAllReferences());
    }
}
