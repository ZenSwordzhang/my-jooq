package com.zsx.design.pattern.abstraction.factory;

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
