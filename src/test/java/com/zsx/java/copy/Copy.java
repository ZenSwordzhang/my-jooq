package com.zsx.java.copy;

public class Copy implements Cloneable {

    public Integer id;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
