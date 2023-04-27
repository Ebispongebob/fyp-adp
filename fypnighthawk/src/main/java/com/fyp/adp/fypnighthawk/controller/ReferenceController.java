package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.rule.entity.Reference;
import com.fyp.adp.basedata.rule.service.ReferenceService;
import com.fyp.adp.common.ro.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get/query")
    public Result queryReferenceById(@RequestParam String referenceId) {
        return Result.success(referenceService.getReferenceById(referenceId));
    }

    @PostMapping("/save")
    public Result saveReference(@RequestBody Reference datasource) {
        return Result.success(referenceService.addReference(datasource));
    }

    @GetMapping("/del")
    public Result delReference(@RequestParam String referenceId) {
        return Result.success(referenceService.deleteReferenceById(referenceId));
    }
}
