package com.zsx.design.pattern.structural.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoratorTest {

    @Test
    void testOperation() {

        Component componentA = new ConcreteDecoratorA(new ConcreteComponent());
        Component componentB = new ConcreteDecoratorB(new ConcreteComponent());

        assertEquals("ConcreteDecoratorA-Decorator-ConcreteComponent", componentA.operation());
        assertEquals("ConcreteDecoratorB-Decorator-ConcreteComponent", componentB.operation());
    }
}
