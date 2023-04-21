package com.fyp.adp.fypnighthawk.config;

import com.fyp.adp.fypnighthawk.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsFilterConfiguration {

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/api/*"); // 您可以根据需要调整此处的 URL 模式
        registrationBean.setOrder(1); // 设置过滤器的优先级，数值越低优先级越高
        return registrationBean;
    }
}
