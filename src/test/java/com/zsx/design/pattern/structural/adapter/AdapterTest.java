package com.zsx.design.pattern.structural.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdapterTest {

    @Test
    void testDemand() {
        Assertions.assertEquals("SPECIAL", new Adapter(new Adaptee()).demand());
    }
}
