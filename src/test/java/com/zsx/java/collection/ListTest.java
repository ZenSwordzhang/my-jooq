package com.zsx.java.collection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testSubList() {
        List<String> list = List.of("abc", "efgh", "ijklm", "ABCDE", "FGHIJK");
        List<String> subList = list.subList(0, 2);
        assertLinesMatch(List.of("abc", "efgh"), subList);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void findFirstAndAny() {

        List<String> list1 = Arrays.asList("Bill", "David", "Jack", "Duke", "Jill", "Dany", "Julia", "Jeff", "Divya");
        List<String> list2 = Arrays.asList("Bill2", "David2", "Jack2", "Duke2", "Jill2", "Dany2", "Julia2", "Jeff2", "Divya2");

        int count = 0;
        for (int i = 0; i < 1000; i++) {
            Optional<String> firstStr = list1.stream().filter(s -> s.startsWith("D")).findFirst();
            // 串行会返回第一个值
            Optional<String> anyStr = list2.stream().filter(s -> s.startsWith("J")).findAny();
            assertEquals("David", firstStr.get());
            assertEquals("Jack2", anyStr.get());

            Optional<String> firstStr2 = list1.parallelStream().filter(s -> s.startsWith("D")).findFirst();
            // 并行在找到的值中随机返回一个
            Optional<String> anyStr2 = list2.parallelStream().filter(s -> s.startsWith("J")).findAny();
            assertEquals("David", firstStr2.get());
            if ("Jill2".equals(anyStr2.get())) {
                count++;
            }
        }
        assertNotEquals(0, count);
        assertNotEquals(1000, count);
    }
}
