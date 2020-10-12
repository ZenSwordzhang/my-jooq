package com.zsx.design.pattern.structural.adapter;


public class Adapter implements ITarget {

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String demand() {
        return adaptee.specialRequirements();
    }

}
