package com.zsx.design.pattern.creational.abstraction.factory;

public class DellFactory extends AbstractFactory {

    @Override
    public IMouse createMouse() {
        return new DellMouse();
    }

    @Override
    public IKeyboard creatKeyboard() {
        return new DellKeyboard();
    }

}
