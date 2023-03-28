package com.fyp.adp.rule.engine;

import com.fyp.adp.rule.engine.config.Configuration;
import com.fyp.adp.rule.engine.info.MatchInfo;
import com.fyp.adp.rule.engine.info.SinkInfo;
import com.fyp.adp.rule.engine.info.SourceInfo;
import jdk.internal.event.Event;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;
import java.util.Map;

public class AdpRtRuleEngine extends BaseRtEngine {
    public AdpRtRuleEngine(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected void initSink(StreamExecutionEnvironment env, Configuration configuration, SinkInfo sinkInfo, DataStream<Map<String, List<Event>>> filterStream) {

    }

    @Override
    protected DataStream<Map<String, List<Event>>> initMatcher(StreamExecutionEnvironment env, Configuration configuration, MatchInfo matchInfo, DataStream<Event> sourceStream) {
        return null;
    }

    @Override
    protected DataStream<Event> initSource(StreamExecutionEnvironment env, Configuration configuration, SourceInfo sourceInfo) {
        return null;
    }
}
