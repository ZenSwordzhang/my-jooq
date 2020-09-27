package com.zsx.java.util.function;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplierTest {

    @Test
    void testGet() {
        Supplier<String> supplier = () -> "Hello World";
        assertEquals("Hello World", supplier.get());
    }
}
