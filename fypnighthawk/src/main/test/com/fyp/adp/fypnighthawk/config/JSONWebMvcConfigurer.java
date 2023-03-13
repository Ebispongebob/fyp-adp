package com.fyp.adp.fypnighthawk.config;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author Pumpkin
 * @createTime 2022/12/31 21:38
 */
@Configuration
public class JSONWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public FastJsonConfig fastJsonConfig(){
        // 1. 自定义配置
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 2.1 配置序列化的行为
        config.setWriterFeatures(JSONWriter.Feature.PrettyFormat);

        // 2.2 配置反序列化的行为
        config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);

        return config;
    }
    
	/**
     * 使用 FastJsonHttpMessageConverter 来替换 Spring MVC 默认的 HttpMessageConverter
     * 以提高 @RestController @ResponseBody @RequestBody 注解的 JSON序列化和反序列化速度。
     * 
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1. 转换器
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setFastJsonConfig(fastJsonConfig());
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(0, converter);
    }
}