package com.zsx.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zsx.enumeration.ErrorLevel;
import com.zsx.enumeration.WeekDay;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

@Log4j2
@Component
public class LogCollector {

    private final static List<Object> synList = Collections.synchronizedList(Lists.newArrayList());

    @Scheduled(fixedRate = 10*60*1000)
    public void run() {
        consume();
    }

    public void produce(Object obj) {
        synList.add(obj);
    }

    private void consume() {
        if (synList.size() !=0 ) {
            synchronized (synList) {
                if (synList.size() != 0) {
                    sendPost(JSON.toJSONString(synList));
                    synList.clear();
                }
            }
        }
    }

    private void sendPost(Object obj) {
        try {
            //create RestTemplate
            String baseUrl = "http://zsx-2.local:8088";

            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);

            SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(5000);
            clientHttpRequestFactory.setReadTimeout(5000);

            RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("logstash_writer_user", "123456").build();
            restTemplate.setUriTemplateHandler(factory);

            // Solve the Chinese garbled problem
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<Object> sendEntity = new HttpEntity<>(obj, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity("/", sendEntity, String.class);

            //parse response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("success:{}", responseEntity.getBody());
            } else {
                log.error("error,statusCode:{},return:{}", responseEntity.getStatusCode().value(), responseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Failed to send error logs to logstash", e);
        }
    }


}
