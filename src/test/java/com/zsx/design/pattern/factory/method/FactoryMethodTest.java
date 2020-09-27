package com.zsx.design.pattern.factory.method;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryMethodTest {

    @Test
    void testGetProduct() {
        IFactory factory = new TVFactory();
        IProduct product = factory.produce();
        assertEquals("TV", product.getProduct());

        IFactory factory2 = new CarFactory();
        IProduct product2 = factory2.produce();
        assertEquals("Car", product2.getProduct());
    }
}
