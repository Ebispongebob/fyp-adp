package com.fyp.adp.basedata.user.service;

import com.fyp.adp.basedata.user.mapper.LocalAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: ruitao.wei
 * @Date: 2023/2/17 18:27
 */
@Service
public class AuthService {
    @Autowired
    LocalAuthMapper localAuthMapper;

}
