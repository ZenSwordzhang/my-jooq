package com.zsx.design.pattern.structural.decorator;

public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        return String.format("ConcreteDecoratorA-%s", super.operation());
    }
}
