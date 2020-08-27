package com.zsx.utils;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LogCollectorTest {

    @Autowired
    private LogCollector logCollector;

    @Test
    void testProduce() {
        List<String> list = Lists.newArrayList();
        String json1 = " { \"msg\":\"BizException FATAL\",\"level\":\"FATAL\"} ";
        String json2 = " { \"msg\":\"RunTimeException\",\"level\":\"Error\"} ";
        String json3 = " { \"msg\":\"BizException FATAL1\",\"level\":\"FATAL1\"} ";
        list.add(json1);
        list.add(json2);
        list.add(json3);
        System.out.println(list);
        // logstash通过codec => "json"会将json数组拆分成多个文档保存
        logCollector.produce(list.toString());
    }
}
