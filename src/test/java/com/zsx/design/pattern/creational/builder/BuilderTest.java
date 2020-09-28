package com.zsx.design.pattern.creational.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuilderTest {

    @Test
    void testBuildProduct() {
        Product product = new Director(new ConcreteBuilder()).buildProduct();
        assertEquals("Skeleton", product.getSkeleton());
        assertEquals("Engine", product.getEngine());
        assertEquals("Wheel", product.getWheel());
    }
}
