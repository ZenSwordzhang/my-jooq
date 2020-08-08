package com.zsx.utils;

import com.google.common.collect.Lists;
import com.zsx.entity.Author;
import com.zsx.enumeration.ErrorLevel;
import com.zsx.enumeration.WeekDay;
import com.zsx.exception.BizException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class LogCollector {


    private static final List<String> list = Lists.newArrayList();

    private final static List<String> synList = Collections.synchronizedList(list);

    // 模拟生成日志
    private void generateLog() throws Exception {
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


    public static void main(String[] args) throws InterruptedException {
        LogCollector collector = new LogCollector();

        JSONObject jsonObj = new JSONObject();
        Author author1 = Author.of("zsx", 20, true, "1");
//        jsonObj.put("author1", author1);
        Author author2 = Author.of("lisisi", 18, false, "2");
//        jsonObj.put("author2", author2);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(author1);
        jsonArray.put(author2);
        collector.sendPost(jsonArray.get(0));
        System.out.println(jsonArray.get(1));
//        while (true) {
//            int m = 100 + new Random().nextInt(100);
//            JSONArray intervalMsg = new JSONArray();
//            for (int i = 0; i < m; i++) {
//                try {
//                    collector.generateLog();
//                } catch (Exception e) {
//                    JSONArray msgArray = new JSONArray();
//                    // 如果是BizException，只收集FATAL等级日志
//                    if (e instanceof BizException) {
//                        BizException bizException = (BizException) e;
//                        if (ErrorLevel.FATAL.equals(bizException.getLevel())) {
//                            Map<String, Object> msgMap = Map.of("msg", bizException.getMessage(), "level", bizException.getLevel());
//                            msgArray.put(msgMap);
//                            // 收集日志到缓存
//                            list.add(msgArray.toString());
//                        }
//                    } else {
//                        JSONObject jsonObj = new JSONObject();
//                        jsonObj.put("msg", e.getMessage());
//                        jsonObj.put("level", "Error");
//                        msgArray.put(jsonObj);
//                        // 收集日志到缓存
//                        list.add(msgArray.toString());
//                    }
//                }
//            }
//            intervalMsg.put(list);
//            System.out.println(list);
//            // 定时发送收集到的日志到Logstash
//            System.out.println(intervalMsg.toString());
//            // 十秒钟发送一次数据
//            TimeUnit.SECONDS.sleep(10);
//        }

    }

    public void sendPost(Object obj) {
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
