package com.fyp.adp.basedata.user.service;

import com.fyp.adp.basedata.user.entity.UserProfile;
import com.fyp.adp.basedata.user.mapper.UserProfileMapper;
import com.fyp.adp.common.constants.InterceptorConstants;
import com.fyp.adp.common.enums.ReturnStatus;
import com.fyp.adp.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.Objects;

/**
 * @Description: TODO
 * @Author: ruitao.wei
 * @Date: 2023/2/17 18:25
 */
@Service
public class UserService {
    @Autowired
    UserProfileMapper userProfileMapper;

    public UserProfile getUserProfileById(Long id) {
        return userProfileMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取当前用户
     * @param: token token
     * @return: 当前用户
     */
    public UserProfile getCurrentUser(String token) {
        String[] sequences = token.split(":");
        if (sequences.length != 3) {
            throw new BusinessException(ReturnStatus.SC_BAD_REQUEST, "用户token格式不准确");
        }
        String           userCode = sequences[1];
        Example          example  = new Example(UserProfile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", userCode);
        UserProfile userProfile = userProfileMapper.selectOneByExample(example);
        if (Objects.isNull(userProfile)) {
            throw new BusinessException(ReturnStatus.SC_BAD_REQUEST, "用户不存在");
        }
        return userProfile;
    }
}
