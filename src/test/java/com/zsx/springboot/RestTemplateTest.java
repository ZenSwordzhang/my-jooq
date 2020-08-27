package com.zsx.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

    /**
     * Solve the Chinese garbled problem
     */
    @Test
    void testGibberishCharacters() {
        RestTemplate restTemplate = new RestTemplate();
    }
}
