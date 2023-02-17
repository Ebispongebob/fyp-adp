package com.fyp.adp.fypnighthawk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = { "com.fyp.adp" })
@MapperScan(basePackages = { "com.fyp.adp.**.mapper" })
public class AdpApplication {
    private static Logger logger = LoggerFactory.getLogger(AdpApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdpApplication.class, args);
        logger.info("application started----------------------");
    }
}