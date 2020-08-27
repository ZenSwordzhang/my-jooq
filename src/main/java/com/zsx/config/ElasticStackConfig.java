package com.zsx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "elastic.stack")
@Configuration
@Data
public class ElasticStackConfig {

    private Logstash logstash = new Logstash();

    @Data
    public static class Logstash {

        private String url;

        private String username;

        private String password;

        private long sendInterval = 100000;

        private int sendTimeout = 100000;
    }
}
