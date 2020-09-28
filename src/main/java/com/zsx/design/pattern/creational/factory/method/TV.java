package com.zsx.design.pattern.creational.factory.method;

public class TV implements IProduct {

    @Override
    public String getProduct() {
        System.out.println("TV production success");
        return "TV";
    }
}
