package com.fyp.adp.fypnighthawk.initer;

import com.fyp.adp.basedata.rule.mapper.ReferenceMapper;
import com.fyp.adp.common.dto.Tuple;
import com.fyp.adp.common.utils.HttpClientUtils;
import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RuleEngineStarter {

    @Autowired
    ReferenceMapper referenceMapper;

//    @PostConstruct
//    public void init() throws InterruptedException {
//        List<Tuple<String, String>> references = referenceMapper.selectAll().stream()
//                                                                .map(reference -> new Tuple<>(reference.getReferenceId(), reference.getTopic()))
//                                                                .collect(Collectors.toList());
//        for (Tuple<String, String> reference : references) {
//            HashMap<String, String> map = Maps.newHashMap();
//            map.put("referenceId", reference.getFirst());
//            map.put("topic", reference.getSecond());
//            // 启动任务
//            HttpClientUtils.doGet("http://127.0.0.1:8090/api/job/create", map);
//            Thread.sleep(10000L);
//        }
//    }
}
