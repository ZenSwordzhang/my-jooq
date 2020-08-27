package com.zsx.controller;

import com.zsx.entity.LogMessage;
import com.zsx.utils.LogCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogCollector logCollector;

    @GetMapping("/msg")
    public LogMessage getLog() {
        LogMessage logMessage = LogMessage.of("test", UUID.randomUUID().toString(), LogLevel.DEBUG);
        log.info(logMessage.toString());
        logCollector.produce(logMessage);
        return logMessage;
    }

    @GetMapping("/test")
    public List<String> testProduce() {
        String json1 = " { \"msg\":\"BizException FATAL\",\"level\":\"FATAL\"} ";
        String json2 = " { \"msg\":\"RunTimeException\",\"level\":\"Error\"} ";
        String json3 = " { \"msg\":\"BizException FATAL1\",\"level\":\"FATAL1\"} ";
        String json4 = " { \"msg\":\"中文乱码 FATAL1\",\"level\":\"FATAL1\"} ";
        List<String> list = List.of(json1, json2, json3, json4);
        log.info("{}", list);
        // logstash通过codec => "json"会将json数组拆分成多个文档保存
        logCollector.produce(list);
        return list;
    }
}
