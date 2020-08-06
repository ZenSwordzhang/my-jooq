package com.zsx.java.collection;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SynchronizedListTest {

    private static final List<String> list = Lists.newArrayList();

    private final static List<String> synList = Collections.synchronizedList(list);

    @Test
    void testAddAndClear() {
        assertEquals(0, list.size());
        assertEquals(list.size(), synList.size());

        // list改变synList也同时改变
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(synList.contains("A"));
        assertTrue(synList.contains("B"));
        assertTrue(synList.contains("C"));
        assertEquals(list.size(), synList.size());

        list.clear();
        assertEquals(0, synList.size());

        // synList改变list也同时改变
        synList.add("E");
        synList.add("F");
        synList.add("G");
        assertTrue(list.contains("E"));
        assertTrue(list.contains("F"));
        assertTrue(list.contains("G"));
        assertEquals(list.size(), synList.size());

        synList.clear();
        assertEquals(0, list.size());
        assertEquals(list.size(), synList.size());
    }
}
