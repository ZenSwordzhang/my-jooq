package com.zsx.design.pattern.structural.decorator;

public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        return String.format("ConcreteDecoratorB-%s", super.operation());
    }
}
