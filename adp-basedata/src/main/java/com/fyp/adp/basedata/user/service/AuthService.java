package com.fyp.adp.basedata.user.service;

import com.fyp.adp.basedata.user.entity.LocalAuth;
import com.fyp.adp.basedata.user.entity.UserProfile;
import com.fyp.adp.basedata.user.mapper.LocalAuthMapper;
import com.fyp.adp.common.constants.InterceptorConstants;
import com.fyp.adp.common.exception.AuthException;
import com.fyp.adp.common.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 负责用户信息、状态校验
 * @Author: ruitao.wei
 * @Date: 2023/2/17 18:27
 */
@Service
public class AuthService {
    @Autowired
    LocalAuthMapper localAuthMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 登录
     * @param account 账号
     * @param password 密码
     **/
    public String doLogin(String account, String password) {
        //获取用户信息
        LocalAuth localAuth = getAuth(account, password);
        //颁发Token
        return setAuthToken(localAuth);
    }

    /**
     * 获取Auth信息
     * @param account 账号
     * @param password 密码
     * @return Auth信息
     **/
    private LocalAuth getAuth(String account, String password) {
        Example          example  = new Example(LocalAuth.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", account);
        LocalAuth localAuth = localAuthMapper.selectOneByExample(example);
        if (Objects.isNull(localAuth)) {
            throw new AuthException("用户名不存在");
        }
        if (!StringUtils.equals(DigestUtils.md5DigestAsHex(password.getBytes()), localAuth.getPassword())) {
            throw new AuthException("用户名或密码不正确");
        }
        return localAuth;
    }

    /**
     * 颁发token
     * @param localAuth 登录用户信息
     * @return token
     */
    private String setAuthToken(LocalAuth localAuth) {
        // 前缀 + userCode + MD5(密钥+userCode+时间戳)
        String token = InterceptorConstants.COMMON_TOKEN_PREFIX
                + localAuth.getUserCode() + ":"
                + MD5Utils.generate(localAuth.getUserCode(), InterceptorConstants.SECRET,
                                    String.valueOf(System.currentTimeMillis()));
        // 30min过期
        stringRedisTemplate.opsForValue().set(token, localAuth.getUserCode(), 30, TimeUnit.MINUTES);
        return token;
    }
}
