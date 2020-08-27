package com.zsx.config;

import com.zsx.utils.LogCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public LogCollector logCollector() {
        return new LogCollector();
    }
}
