package com.fyp.adp.basedata.rule.service;

import com.fyp.adp.basedata.rule.entity.EventRecord;
import com.fyp.adp.basedata.rule.entity.RuleReferenceRel;
import com.fyp.adp.basedata.rule.mapper.EventRecordMapper;
import com.fyp.adp.basedata.rule.mapper.RuleReferenceRelMapper;
import com.fyp.adp.basedata.rule.vo.EventTypeCountVo;
import com.fyp.adp.basedata.rule.vo.RankListVo;
import com.fyp.adp.basedata.rule.vo.RecordListVo;
import com.fyp.adp.basedata.rule.vo.WeekRecordCountVo;
import com.fyp.adp.common.dto.Tuple;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EventService {

    String format = "yyyy-MM-dd hh:mm:ss";

    @Autowired
    RuleReferenceRelMapper ruleReferenceRelMapper;

    @Autowired
    EventRecordMapper eventRecordMapper;

    /**
     * 添加事件来源绑定
     * @param referenceId reference id
     * @param ruleName rule
     * @return 是否成功
     */
    public Integer addEvent(String referenceId, String ruleName) {
        RuleReferenceRel rel = new RuleReferenceRel();
        rel.setRuleName(ruleName);
        rel.setReferenceId(referenceId);
        return ruleReferenceRelMapper.insert(rel);
    }

    /**
     * 获取事件记录列表
     * @return 事件记录列表
     */
    public List<RecordListVo> get20Records() {
        Example example = new Example(EventRecord.class);
        example.orderBy("eventTime").desc();
        RowBounds rowBounds = new RowBounds(0, 20);
        return eventRecordMapper.selectByExampleAndRowBounds(example, rowBounds).stream().map(eventRecord -> eventRecord2vo.apply(eventRecord)).collect(Collectors.toList());
    }

    /**
     * EventRecord2Vo
     */
    private Function<EventRecord, RecordListVo> eventRecord2vo = eventRecord -> {
        RecordListVo recordListVo = new RecordListVo();
        recordListVo.setReferenceId(eventRecord.getReferenceId());
        recordListVo.setEventType(eventRecord.getEventType());
        recordListVo.setEventTime(new SimpleDateFormat(format).format(eventRecord.getEventTime()));
        return recordListVo;
    };

    /**
     * get Event Rank List
     */
    public List<RankListVo> getRankList() {
        return eventRecordMapper.selectRankByCountDesc();
    }

    /**
     * get Event Rate List
     */
    public List<Tuple<String, Double>> getEventRateList() {
        return eventRecordMapper.queryEventRateList().stream().map(rate -> new Tuple<>(rate.getEvent_type(), Double.parseDouble(String.format("%.1f", rate.getRate() * 100))))
                                .collect(Collectors.toList());
    }

    /**
     * get Last Week Record Count List
     */
    public List<Long> getLastWeekEventCounts() {
        return eventRecordMapper.queryWeekRecordCount().stream().map(WeekRecordCountVo::getCount).collect(Collectors.toList());
    }

    /**
     * get Event Type Count
     */
    public EventTypeCountVo getEventTypeCounts() {
        return eventRecordMapper.queryEventTypeCount();
    }
}
