package com.fyp.adp.basedata.rule.service;

import com.fyp.adp.basedata.rule.entity.EventRecord;
import com.fyp.adp.basedata.rule.entity.EventReferenceRel;
import com.fyp.adp.basedata.rule.mapper.EventRecordMapper;
import com.fyp.adp.basedata.rule.mapper.EventReferenceRelMapper;
import com.fyp.adp.basedata.rule.vo.RankListVo;
import com.fyp.adp.basedata.rule.vo.RecordListVo;
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
    EventReferenceRelMapper eventReferenceRelMapper;

    @Autowired
    EventRecordMapper eventRecordMapper;

    /**
     * 添加事件来源绑定
     * @param event 事件 & 来源
     * @return 是否成功
     */
    public Integer addEvent(EventReferenceRel event) {
        return eventReferenceRelMapper.insert(event);
    }

    /**
     * 获取事件记录列表
     * @return 事件记录列表
     */
    public List<RecordListVo> get10Records() {
        Example example = new Example(EventRecord.class);
        example.orderBy("eventTime").desc();
        RowBounds rowBounds = new RowBounds(0, 10);
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
     * @return Rank List
     */
    public List<RankListVo> getRankList() {
        return eventRecordMapper.selectRankByCountDesc();
    }
}
