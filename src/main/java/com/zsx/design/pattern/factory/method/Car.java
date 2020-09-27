package com.zsx.design.pattern.factory.method;

public class Car implements IProduct {

    @Override
    public String getProduct() {
        System.out.println("Car production success");
        return "Car";
    }
}
