package com.zsx.design.pattern.simple.factory;

public class Square implements IShape {

    @Override
    public String draw() {
        System.out.println("draw a square");
        return "square";
    }
}
