package com.zsx.design.pattern.behavioral.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IteratorTest {

    @Test
    void testNext() {
        Container<String> container = new ConcreteContainer<>();
        container.add("A");
        container.add("B");
        container.add("C");

        Iterator<String> it = container.iterator();
        while (it.hasNext()) {
            Assertions.assertTrue(List.of("A", "B", "C").contains(it.next()));
        }
    }
}
