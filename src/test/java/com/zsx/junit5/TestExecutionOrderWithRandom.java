package com.zsx.junit5;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class TestExecutionOrderWithRandom {

    @Test
    void aTest() {
        System.out.println("=======AA=======");
    }

    @Test
    void bTest() {
        System.out.println("=======BB=======");
    }

    @Test
    void cTest() {
        System.out.println("=======CC=======");
    }
}
