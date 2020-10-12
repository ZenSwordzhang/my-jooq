package com.zsx.java.copy;

public class ShallowCopy implements Cloneable {

    public String name;

    public int age;

    public Copy copy;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
