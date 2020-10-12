package com.zsx.design.pattern.creational.adapter;


public class Adapter implements Target {

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String demand() {
        return adaptee.specialRequirements();
    }

}
