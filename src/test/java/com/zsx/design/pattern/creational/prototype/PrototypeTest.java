package com.zsx.design.pattern.creational.prototype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrototypeTest {

    @Test
    void testClone() {
        Prototype prototype = new Prototype();
        prototype.name = "ZhangSan";

        Prototype cloneObj = (Prototype)prototype.clone();
        Assertions.assertEquals(prototype.name, cloneObj.name);

    }
}
