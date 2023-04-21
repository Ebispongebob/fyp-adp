package com.fyp.adp.fypnighthawk.interceptor;

import com.fyp.adp.common.exception.AuthException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Enumeration;

import static com.fyp.adp.common.constants.InterceptorConstants.BOARD_TOKEN_NAME;
import static com.fyp.adp.common.constants.InterceptorConstants.DEFAULT_TOKEN;

/**
 * @Description: 通用Session拦截器
 * @Author: ruitao.wei
 * @Date: 2023/2/21 18:07
 */
public class CommonSessionInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CommonSessionInterceptor.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authToken = request.getHeader(BOARD_TOKEN_NAME);

        if (StringUtils.isEmpty(authToken)) {
            throw new AuthException("Token is empty！please login！");
        }

        //默认token 不校验权限
        if (DEFAULT_TOKEN.equals(authToken)) {
            return true;
        }
        String tokenValue = stringRedisTemplate.opsForValue().get(authToken);
        if (StringUtils.isEmpty(tokenValue)) {
            throw new AuthException("Token不存在或已失效，请您重新登陆");
        }
        return true;
    }
}
