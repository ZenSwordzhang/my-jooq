package com.zsx.design.pattern.creational.singleton;

/**
 * Static inner class,Thread-safe
 *
 */
public class Singleton4 {

    private Singleton4() {}

    private static class SingletonHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
