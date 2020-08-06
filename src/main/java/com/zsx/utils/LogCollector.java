package com.zsx.utils;

import com.google.common.collect.Lists;
import com.zsx.enumeration.ErrorLevel;
import com.zsx.enumeration.WeekDay;
import com.zsx.exception.BizException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LogCollector {

    private static final List<String> list = Lists.newArrayList();

    private final static List<String> synList = Collections.synchronizedList(list);

    // 模拟生成日志
    private void generateLog() throws Exception {
        WeekDay weekDay = WeekDay.get(new Random().nextInt(7));
        if (weekDay == null) {
            throw new BizException(ErrorLevel.ERROR, "BizException:ERROR:Null");
        }
        switch (weekDay) {
            case SUN -> throw new RuntimeException("RunTimeException");
            case MON -> throw new BizException(ErrorLevel.WARNING, "BizException:WARNING");
            case TUE -> throw new BizException(ErrorLevel.ERROR, "BizException:ERROR");
            case WEN, THU, FRI, SAT -> throw new BizException(ErrorLevel.FATAL, "BizException:FATAL");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        LogCollector collector = new LogCollector();
        while (true) {
            int m = 100 + new Random().nextInt(100);
            JSONArray intervalMsg = new JSONArray();
            for (int i = 0; i < m; i++) {
                try {
                    collector.generateLog();
                } catch (Exception e) {
                    JSONArray msgArray = new JSONArray();
                    // 如果是BizException，只收集FATAL等级日志
                    if (e instanceof BizException) {
                        BizException bizException = (BizException)e;
                        if (ErrorLevel.FATAL.equals(bizException.getLevel())) {
                            Map<String, Object> msgMap = Map.of("msg", bizException.getMessage(), "level", bizException.getLevel());
                            msgArray.put(msgMap);
                            // 收集日志到缓存
                            list.add(msgArray.toString());
                        }
                    } else {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("msg", e.getMessage());
                        jsonObj.put("level", "Error");
                        msgArray.put(jsonObj);
                        // 收集日志到缓存
                        list.add(msgArray.toString());
                    }
                }
            }
            intervalMsg.put(list);
            // 定时发送收集到的日志到Logstash
            System.out.println(intervalMsg.toString());
            // 十秒钟发送一次数据
            TimeUnit.SECONDS.sleep(10);
        }

    }

}
