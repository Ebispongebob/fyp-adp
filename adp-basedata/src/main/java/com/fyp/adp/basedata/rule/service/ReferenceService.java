package com.fyp.adp.basedata.rule.service;

import com.fyp.adp.basedata.rule.mapper.ReferenceMapper;
import com.fyp.adp.basedata.rule.entity.Reference;
import com.fyp.adp.basedata.rule.vo.ReferenceVo;
import com.fyp.adp.common.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public int deleteReferenceById(Long id) {
        return referenceMapper.deleteByPrimaryKey(id);
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
    public Reference getReferenceById(Long id) {
        return referenceMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取全部
     */
    public List<ReferenceVo> getAllReferences() {
        List<Reference> references = referenceMapper.selectAll();
        return references.stream().map(reference -> reference2Vo.apply(reference)).collect(Collectors.toList());
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

