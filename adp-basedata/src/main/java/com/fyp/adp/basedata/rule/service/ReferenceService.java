package com.fyp.adp.basedata.rule.service;

import com.fyp.adp.basedata.rule.mapper.ReferenceMapper;
import com.fyp.adp.basedata.rule.entity.Reference;
import com.fyp.adp.basedata.rule.vo.ReferenceVo;
import com.fyp.adp.common.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReferenceService {

    @Autowired
    ReferenceMapper referenceMapper;

    /**
     * 新增reference
     */
    public int addReference(Reference reference) {
        return referenceMapper.insert(reference);
    }

    /**
     * 删除reference
     */
    public int deleteReferenceById(String referenceId) {
        Example          example  = new Example(Reference.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("referenceId", referenceId);
        return referenceMapper.deleteByExample(example);
    }

    /**
     * 更新reference
     */
    public int updateReference(Reference reference) {
        return referenceMapper.updateByPrimaryKeySelective(reference);
    }

    /**
     * 获取ReferenceById
     */
    public List<ReferenceVo> getReferenceById(String referenceId) {
        Example          example  = new Example(Reference.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orLike("referenceId", "%" + referenceId + "%");
        return referenceMapper.selectByExample(example).stream().sorted((o1, o2) -> o1.getCreateTime().before(o2.getCreateTime()) ? 1 : -1).map(reference -> reference2Vo.apply(reference)).collect(Collectors.toList());
    }

    /**
     * 获取全部
     */
    public List<ReferenceVo> getAllReferences() {
        List<Reference> references = referenceMapper.selectAll();
        Stream<Reference> sorted   = references.stream().sorted((o1, o2) -> o1.getCreateTime().before(o2.getCreateTime()) ? 1 : -1);
        return sorted.map(reference2Vo).collect(Collectors.toList());
    }

    private Function<Reference, ReferenceVo> reference2Vo = reference -> {
        ReferenceVo vo = new ReferenceVo();
        vo.setReferenceId(reference.getReferenceId());
        vo.setDescription(reference.getDescription());
        vo.setTopic(reference.getTopic());
        vo.setBrokerAddr(reference.getBrokerAddr());
        vo.setCreateTime(DateUtils.format(reference.getCreateTime()));
        return vo;
    };

    /**
     * 查询分页
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 查询结果
     */
    public PageInfo<Reference> getReferencesByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Reference> references = referenceMapper.selectAll();
        return new PageInfo<>(references);
    }
}

