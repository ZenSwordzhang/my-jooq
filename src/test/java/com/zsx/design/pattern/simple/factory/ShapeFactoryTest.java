package com.zsx.design.pattern.simple.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShapeFactoryTest {

    @Test
    void testDraw() {
        ShapeFactory shapeFactory = new ShapeFactory();

        IShape shape = shapeFactory.getShap("CIRCLE");
        assertEquals("circle", shape.draw());

        IShape shape1 = shapeFactory.getShap("RECTANGLE");
        assertEquals("rectangle", shape1.draw());

        IShape shape2 = shapeFactory.getShap("SQUARE");
        assertEquals("square", shape2.draw());
    }
}
