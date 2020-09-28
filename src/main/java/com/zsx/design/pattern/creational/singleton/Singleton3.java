package com.zsx.design.pattern.creational.singleton;

/**
 * Lazy,Thread-safe
 * DSL(double-checked locking)
 */
public class Singleton3 {

    private static Singleton3 instance;

    private Singleton3() {}

    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized(Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
