package com.zsx.design.pattern.creational.factory.method;


public class TVFactory implements IFactory {

    @Override
    public IProduct produce() {
        return new TV();
    }

}
