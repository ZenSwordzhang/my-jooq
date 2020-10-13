package com.zsx.design.pattern.behavioral.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyTest {

    @Test
    void testContextInterface() {
        Context contextA = new Context(new ConcreteStrategyA());
        Context contextB = new Context(new ConcreteStrategyB());
        Context contextC = new Context(new ConcreteStrategyC());

        assertEquals("StrategyA", contextA.contextInterface());
        assertEquals("StrategyB", contextB.contextInterface());
        assertEquals("StrategyC", contextC.contextInterface());
    }
}
