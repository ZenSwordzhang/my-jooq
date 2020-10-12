package com.zsx.java.copy;

public class DeepCopy implements Cloneable {

    public String name;

    public int age;

    public Copy copy;

    @Override
    public Object clone() {
        try {
            DeepCopy cloneObj = (DeepCopy)super.clone();
            cloneObj.copy = (Copy)this.copy.clone();
            return cloneObj;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
