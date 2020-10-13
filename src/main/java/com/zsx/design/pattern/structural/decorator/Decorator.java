package com.zsx.design.pattern.structural.decorator;

public class Decorator implements Component {

    private final Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        return String.format("Decorator-%s", component.operation());
    }
}
