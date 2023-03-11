package com.fyp.adp.fypnighthawk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author: ruitao.wei
 * @Date: 2023-03-06
 * @Desc: Doris config
 **/
@Configuration
public class DorisConfig {
    private static Logger logger = LoggerFactory.getLogger(DorisConfig.class);

//    @Bean(name = "dorisDataSource")
//    @ConfigurationProperties(prefix = "datasource.doris")
//    public DataSource prestoDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "dorisTemplate")
//    public JdbcTemplate dorisJdbcTemplate(@Qualifier("dorisDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}