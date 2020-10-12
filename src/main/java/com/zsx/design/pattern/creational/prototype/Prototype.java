package com.zsx.design.pattern.creational.prototype;

public class Prototype implements Cloneable {

    public String name;

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
