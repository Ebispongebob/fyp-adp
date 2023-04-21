package com.fyp.adp.basedata.rule.mapper;

import com.fyp.adp.basedata.rule.entity.EventRecord;
import com.fyp.adp.basedata.rule.vo.RankListVo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EventRecordMapper extends Mapper<EventRecord> {
    @Select(value = "SELECT reference_id, count( 1 ) AS count FROM event_record GROUP BY reference_id ORDER BY count DESC LIMIT 10")
    List<RankListVo> selectRankByCountDesc();
}
