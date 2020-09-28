package com.zsx.design.pattern.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Eager Singleton
 */
public class SingletonTest {

    @Test
    void testGetInstance() {
        assertSame(Singleton.getInstance(), Singleton.getInstance());
    }

    @Test
    void testGetInstance1() {
        assertSame(Singleton1.getInstance(), Singleton1.getInstance());
    }

    @Test
    void testGetInstance2() {
        assertSame(Singleton2.getInstance(), Singleton2.getInstance());
    }

    @Test
    void testGetInstance3() {
        assertSame(Singleton3.getInstance(), Singleton3.getInstance());
    }

    @Test
    void testGetInstance4() {
        assertSame(Singleton4.getInstance(), Singleton4.getInstance());
    }

    @Test
    void testEnum() {
        assertSame(Singleton5.INSTANCE, Singleton5.INSTANCE);
    }

}
