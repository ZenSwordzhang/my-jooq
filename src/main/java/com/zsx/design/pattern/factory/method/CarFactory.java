package com.zsx.design.pattern.factory.method;


public class CarFactory implements IFactory {

    @Override
    public IProduct produce() {
        return new Car();
    }
}

