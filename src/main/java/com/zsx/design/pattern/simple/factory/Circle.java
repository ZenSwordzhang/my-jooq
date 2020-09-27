package com.zsx.design.pattern.simple.factory;

public class Circle implements IShape {

    @Override
    public String draw() {
        System.out.println("draw a circle");
        return "circle";
    }
}
