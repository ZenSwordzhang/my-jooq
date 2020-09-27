package com.zsx.design.pattern.factory.method;


public class TVFactory implements IFactory {

    @Override
    public IProduct produce() {
        return new TV();
    }

}
