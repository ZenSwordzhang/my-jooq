package com.zsx.design.pattern.creational.simple.factory;

public class Rectangle implements IShape {

    @Override
    public String draw() {
        System.out.println("draw a rectangle");
        return "rectangle";
    }
}
