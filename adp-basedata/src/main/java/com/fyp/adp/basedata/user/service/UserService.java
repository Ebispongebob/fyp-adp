package com.fyp.adp.basedata.user.service;

import com.fyp.adp.basedata.user.entity.UserProfile;
import com.fyp.adp.basedata.user.mapper.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
