package com.zsx.design.pattern.creational.factory.method;


public class CarFactory implements IFactory {

    @Override
    public IProduct produce() {
        return new Car();
    }
}

