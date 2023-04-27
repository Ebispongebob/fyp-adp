package com.fyp.adp.basedata.rule.service;

import com.fyp.adp.basedata.rule.entity.RuleConfig;
import com.fyp.adp.basedata.rule.mapper.RuleConfigMapper;
import com.fyp.adp.basedata.rule.vo.RuleConfigVo;
import com.fyp.adp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RuleService {

    @Autowired
    RuleConfigMapper ruleConfigMapper;

    /**
     * 获取全部rule
     */
    public List<RuleConfigVo> getAllRules() {
        Stream<RuleConfig> sorted = ruleConfigMapper.selectAll().stream().sorted((o1, o2) -> o1.getCreateTime().before(o2.getCreateTime()) ? 1 : -1);
        return sorted.map(ruleConfig2Vo).collect(Collectors.toList());
    }

    /**
     * 通过rule name查询
     */
    public List<RuleConfigVo> queryRules(String ruleName) {
        Example          example  = new Example(RuleConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("ruleName", "%" + ruleName + "%");
        return ruleConfigMapper.selectByExample(example).stream().sorted((o1, o2) -> o1.getCreateTime().before(o2.getCreateTime()) ? 1 : -1).map(ruleConfig2Vo).collect(
                Collectors.toList());
    }

    private Function<RuleConfig, RuleConfigVo> ruleConfig2Vo = ruleConfig -> {
        RuleConfigVo vo = new RuleConfigVo();
        vo.setRuleName(ruleConfig.getRuleName());
        vo.setAlertConfig(ruleConfig.getAlertConfig());
        vo.setThreshold(ruleConfig.getThreshold());
        vo.setEventType(ruleConfig.getEventType());
        vo.setWindowSize(ruleConfig.getWindowSize());
        vo.setCreateTime(DateUtils.format(ruleConfig.getCreateTime()));
        return vo;
    };

    /**
     *  保存rule
     */
    public int saveRule(RuleConfig ruleConfig) {
        return ruleConfigMapper.insert(ruleConfig);
    }

    /**
     * 通过ruleName删除
     */
    public int delRuleByName(String ruleName) {
        Example          example  = new Example(RuleConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ruleName", ruleName);
        return ruleConfigMapper.deleteByExample(example);
    }

}
