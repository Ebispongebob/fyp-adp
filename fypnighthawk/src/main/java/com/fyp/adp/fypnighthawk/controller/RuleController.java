package com.fyp.adp.fypnighthawk.controller;

import com.fyp.adp.basedata.rule.entity.RuleConfig;
import com.fyp.adp.basedata.rule.service.EventService;
import com.fyp.adp.basedata.rule.service.RuleService;
import com.fyp.adp.common.ro.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rule")
@CrossOrigin(allowedHeaders = "FYP-AUTH-TOKEN")
public class RuleController {

    @Autowired
    EventService eventService;

    @Autowired
    RuleService ruleService;

    @GetMapping("/event/list")
    public Result getRecordList() {
        return Result.success(eventService.get20Records());
    }

    @GetMapping("event/rank")
    public Result getRankList() {
        return Result.success(eventService.getRankList());
    }

    @GetMapping("event/rate")
    public Result getTopEventRate() {
        return Result.success(eventService.getEventRateList());
    }

    @GetMapping("event/week/count")
    public Result getLastWeekEventCounts() {
        return Result.success(eventService.getLastWeekEventCounts());
    }

    @GetMapping("event/type/count")
    public Result getEventTypeCounts() {
        return Result.success(eventService.getEventTypeCounts());
    }

    @GetMapping("event/count")
    public Result getEventCounts() {
        return Result.success(eventService.getEventCounts());
    }

    @GetMapping("/rel/save")
    public Result saveRuleReferenceRel(@RequestParam String referenceId, @RequestParam String ruleName) {
        return Result.success(eventService.addEvent(referenceId, ruleName));
    }

    @GetMapping("/rel/get")
    public Result getRuleReferenceRel(@RequestParam String referenceId) {
        return Result.success(eventService.getRuleReferRel(referenceId));
    }

    @GetMapping("/rel/del")
    public Result delRuleReferenceRel(@RequestParam String referenceId, @RequestParam String ruleName) {
        return Result.success(eventService.delRuleReferRel(referenceId, ruleName));
    }

    @GetMapping("/list")
    public Result getRuleList() {
        return Result.success(ruleService.getAllRules());
    }

    @GetMapping("/list/query")
    public Result queryRuleList(String ruleName) {
        return Result.success(ruleService.queryRules(ruleName));
    }

    @PostMapping("/save")
    public Result saveRule(@RequestBody RuleConfig rule) {
        return Result.success(ruleService.saveRule(rule));
    }

    @GetMapping("/del")
    public Result delRule(@RequestParam String ruleName) {
        return Result.success(ruleService.delRuleByName(ruleName));
    }
}
