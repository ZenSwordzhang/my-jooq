package com.zsx.java.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
public class MapTest {

    @Test
    void testToString() {
        Map<String, String> map = Map.of("1", "zsx", "2", "lisisi");
        log.info(map.toString());
        String[] names = {"zsx", "lisisi", "wangzhaojun"};
        String[] ages = {"20", "18", "18"};
        Map<String, String[]> map1 = Map.of("1", names, "2", ages);
        log.info(map1.toString());
    }
}
