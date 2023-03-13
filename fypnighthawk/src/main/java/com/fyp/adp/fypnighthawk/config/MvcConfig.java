package com.fyp.adp.fypnighthawk.config;

import com.fyp.adp.fypnighthawk.interceptor.CommonSessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author MuxBwf
 */
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public CommonSessionInterceptor commonSessionInterceptor() {
        return new CommonSessionInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要添加多个拦截器的话，调用addInterceptor()多次即可
        registry.addInterceptor(commonSessionInterceptor())
                //拦截器要拦截的url规则
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login")
                .excludePathPatterns("/swagger-ui.html")
                //拦截器的执行顺序
                .order(1);
    }

}
