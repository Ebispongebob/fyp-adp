package com.fyp.adp.basedata.rule.mapper;

import com.fyp.adp.basedata.rule.entity.EventRecord;
import com.fyp.adp.basedata.rule.vo.EventRateListVo;
import com.fyp.adp.basedata.rule.vo.EventTypeCountVo;
import com.fyp.adp.basedata.rule.vo.RankListVo;
import com.fyp.adp.basedata.rule.vo.WeekRecordCountVo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EventRecordMapper extends Mapper<EventRecord> {
    @Select(value = "SELECT reference_id, count( 1 ) AS count FROM event_record GROUP BY reference_id ORDER BY count DESC LIMIT 5")
    List<RankListVo> selectRankByCountDesc();

    @Select(value = "SELECT event_type,\n"
            + "       COUNT(1) / (SELECT COUNT(1) FROM event_record) AS rate\n"
            + "FROM event_record\n"
            + "GROUP BY event_type ORDER BY rate DESC\n "
            + "LIMIT 5;\n")
    List<EventRateListVo> queryEventRateList();

    @Select(value = "SELECT\n"
            + "    dates.date,\n"
            + "    IFNULL(data.count, 0) AS count\n"
            + "FROM (\n"
            + "    SELECT CURDATE() - INTERVAL 6 DAY AS date\n"
            + "    UNION ALL SELECT CURDATE() - INTERVAL 5 DAY\n"
            + "    UNION ALL SELECT CURDATE() - INTERVAL 4 DAY\n"
            + "    UNION ALL SELECT CURDATE() - INTERVAL 3 DAY\n"
            + "    UNION ALL SELECT CURDATE() - INTERVAL 2 DAY\n"
            + "    UNION ALL SELECT CURDATE() - INTERVAL 1 DAY\n"
            + "    UNION ALL SELECT CURDATE()\n"
            + ") dates\n"
            + "LEFT JOIN (\n"
            + "    SELECT DATE(event_time) AS date, COUNT(*) AS count\n"
            + "    FROM event_record\n"
            + "    WHERE event_time >= CURDATE() - INTERVAL 6 DAY\n"
            + "      AND event_time < CURDATE() + INTERVAL 1 DAY\n"
            + "    GROUP BY date\n"
            + ") data ON dates.date = data.date\n"
            + "ORDER BY dates.date;\n")
    List<WeekRecordCountVo> queryWeekRecordCount();

    @Select(value = "SELECT\n"
            + "  SUM(CASE WHEN event_type = 'normal' THEN 1 ELSE 0 END) AS normal,\n"
            + "\tSUM(CASE WHEN event_type = 'weekend' THEN 1 ELSE 0 END) AS warning,\n"
            + "  SUM(CASE WHEN event_type NOT IN ('normal', 'weekend') THEN 1 ELSE 0 END) AS error\n"
            + "FROM event_record;")
    EventTypeCountVo queryEventTypeCount();
}
