package com.zsx.design.pattern.abstraction.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractFactoryTest {

    @Test
    void testProduceMouse() {
        AbstractFactory factory = new LenovoFactory();
        IMouse mouse = factory.createMouse();
        assertEquals("Lenovo mouse", mouse.getMouse());

        AbstractFactory factory2 = new DellFactory();
        IMouse mouse2 = factory2.createMouse();
        assertEquals("Dell mouse", mouse2.getMouse());
    }

    @Test
    void testCreatKeyboard() {
        AbstractFactory factory = new LenovoFactory();
        IKeyboard keyboard = factory.creatKeyboard();
        assertEquals("Lenovo keyboard", keyboard.getKeyboard());

        AbstractFactory factory2 = new DellFactory();
        IKeyboard keyboard2 = factory2.creatKeyboard();
        assertEquals("Dell keyboard", keyboard2.getKeyboard());
    }
}
