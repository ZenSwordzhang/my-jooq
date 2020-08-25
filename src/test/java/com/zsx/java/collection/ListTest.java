package com.zsx.java.collection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ListTest {

    @Test
    void testStream() {
        List<String> list = List.of("1", "2", "3", "1");
        log.info(list.toString());

        List<?> list1 = list.stream().map(str -> {
            if ("1".equals(str)) {
                return List.of();
            }
            return List.of("str", str);
        }).flatMap(Collection :: stream).collect(Collectors.toList());
        log.info(list1.toString());

        List<String> list2 = list.stream().map(str -> {
            if ("1".equals(str)) {
                return null;
            }
            return str;
        }).collect(Collectors.toList());
        log.info(list2.toString());

        List<String> list3 = list.stream().map(str -> {
            if ("1".equals(str)) {
                return null;
            }
            return str;
        }).filter(Objects :: nonNull).collect(Collectors.toList());
        log.info(list3.toString());

        List<String> list4 = Lists.newArrayList();
        list.forEach(str -> {
            if ("1".equals(str)) {
                return;
            }
            list4.add(str);
        });
        log.info(list4.toString());
    }
}
