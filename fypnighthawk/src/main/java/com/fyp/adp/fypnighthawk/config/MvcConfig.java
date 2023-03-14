package com.fyp.adp.fypnighthawk.config;

import com.fyp.adp.common.constants.InterceptorConstants;
import com.fyp.adp.fypnighthawk.interceptor.CommonSessionInterceptor;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My App").version("1.0.0"))
                // Components section defines Security Scheme "mySecretHeader"
                .components(new Components()
                                    .addSecuritySchemes("mySecretHeader", new SecurityScheme()
                                            .type(SecurityScheme.Type.APIKEY)
                                            .in(SecurityScheme.In.HEADER)
                                            .name(InterceptorConstants.BOARD_TOKEN_NAME)))
                // AddSecurityItem section applies created scheme globally
                .addSecurityItem(new SecurityRequirement().addList("mySecretHeader"));
    }
}
