package com.zsx.config;

import com.zsx.error.CustomizeErrorAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.errors.attributes", havingValue = "true")
public class WebErrorConfiguration {

    @Bean
    public ErrorAttributes errorAttributes() {
        log.info("=========WebErrorConfiguration.errorAttributes()=========");
        return new CustomizeErrorAttributes();
    }
}
