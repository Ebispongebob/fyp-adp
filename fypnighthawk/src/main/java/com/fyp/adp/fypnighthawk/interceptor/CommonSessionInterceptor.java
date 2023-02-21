package com.fyp.adp.fypnighthawk.interceptor;

import com.alibaba.fastjson.JSON;
import com.fyp.adp.common.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fyp.adp.common.constants.InterceptorConstants.BOARD_TOKEN_NAME;
import static com.fyp.adp.common.constants.InterceptorConstants.DEFAULT_TOKEN;

/**
 * @Description: 通用Session拦截器
 * @Author: ruitao.wei
 * @Date: 2023/2/21 18:07
 */
public class CommonSessionInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CommonSessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authToken = request.getHeader(BOARD_TOKEN_NAME);
        if (StringUtils.isEmpty(authToken)) {
            throw new AuthException("Token不能为空！请登录！");
        }

        //默认token 不校验权限
        if (DEFAULT_TOKEN.equals(authToken)) {
            return true;
        }
        String biValue = RedisUtils;
        if (StringUtils.isEmpty(biValue)) {
            throw new AuthException("由于长时间未操作，请您重新登陆");
        }
//        LoginEmployee loginEmployee = JSON.parseObject(biValue, LoginEmployee.class);
        // check auth
        return true;
    }

}
