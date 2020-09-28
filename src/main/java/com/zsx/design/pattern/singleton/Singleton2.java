package com.zsx.design.pattern.singleton;

/**
 * Lazy,Thread-safe
 */
public class Singleton2 {

    private static Singleton2 instance;

    private Singleton2() {}

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}
