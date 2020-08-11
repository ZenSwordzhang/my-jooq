package com.zsx.utils;

import com.google.common.collect.Lists;
import com.zsx.enumeration.ErrorLevel;
import com.zsx.enumeration.WeekDay;
import com.zsx.exception.BizException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

@Log4j2
@Component
public class LogCollector {

    private final static List<String> synList = Collections.synchronizedList(Lists.newArrayList());

    @Scheduled(fixedRate = 10*60*1000)
    public void run() {
        synchronized (synList) {
            if (synList.size() !=0 ) {
                synList.clear();
            }
        }
    }

    // 模拟生成日志
    private void generateLog() {
        WeekDay weekDay = WeekDay.get(new Random().nextInt(7));
        if (weekDay == null) {
            throw new BizException(ErrorLevel.ERROR, "BizException ERROR Null");
        }
        switch (weekDay) {
            case SUN -> throw new RuntimeException("RunTimeException");
            case MON -> throw new BizException(ErrorLevel.WARNING, "BizException WARNING");
            case TUE -> throw new BizException(ErrorLevel.ERROR, "BizException ERROR");
            case WEN, THU, FRI, SAT -> throw new BizException(ErrorLevel.FATAL, "BizException FATAL");
        }
    }

    public static void sendPost(Object obj) {
        //create RestTemplate
        String baseUrl = "http://zsx-2.local:8088";
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("logstash_writer_user", "123456").build();
        restTemplate.setUriTemplateHandler(factory);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/", obj, String.class);

        //parse response
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("success:{}", responseEntity.getBody());
        } else {
            log.error("error,statusCode:{},return:{}", responseEntity.getStatusCode().value(), responseEntity.getBody());
        }
    }


}