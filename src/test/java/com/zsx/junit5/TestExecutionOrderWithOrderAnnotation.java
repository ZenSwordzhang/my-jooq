package com.zsx.junit5;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestExecutionOrderWithOrderAnnotation {

    @Order(1)
    @Test
    void aTest() {
        System.out.println("=======AA=======");
    }

    @Order(2)
    @Test
    void bTest() {
        System.out.println("=======BB=======");
    }

    @Order(3)
    @Test
    void cTest() {
        System.out.println("=======CC=======");
    }

}
