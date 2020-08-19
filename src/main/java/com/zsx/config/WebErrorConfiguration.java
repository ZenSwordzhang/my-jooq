package com.zsx.config;

import com.zsx.error.CustomizeErrorAttributes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.errors.attributes", havingValue = "true")
public class WebErrorConfiguration {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new CustomizeErrorAttributes();
    }
}
