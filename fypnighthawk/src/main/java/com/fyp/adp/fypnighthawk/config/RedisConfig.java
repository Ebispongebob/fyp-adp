package com.fyp.adp.fypnighthawk.config;

import com.fyp.adp.basedata.datasource.DatasourceFactory;
import com.fyp.adp.basedata.datasource.enums.DatasourceType;
import com.fyp.adp.basedata.user.entity.DatasourceConfig;
import com.fyp.adp.common.enums.ReturnStatus;
import com.fyp.adp.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * @Description: redis config
 * @Author: ruitao.wei
 * @Date: 2023/3/10 15:18
 */
@Configuration
public class RedisConfig {

    @Autowired
    DatasourceFactory datasourceFactory;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        DatasourceConfig redisConfig = datasourceFactory.getDatasourceConfig(DatasourceType.REDIS);
        if (Objects.isNull(redisConfig)) {
            throw new BusinessException(ReturnStatus.SC_INTERNAL_SERVER_ERROR, "Redis配置获取不到, datasourceName: " + DatasourceType.REDIS.getDefault());
        }
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisConfig.getHost());
        standaloneConfiguration.setPort(Integer.parseInt(redisConfig.getPort()));
        standaloneConfiguration.setPassword(redisConfig.getPassword());
        standaloneConfiguration.setDatabase(0);
        return new JedisConnectionFactory(standaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
