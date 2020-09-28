package com.zsx.design.pattern.abstraction.factory;

public class LenovoFactory extends AbstractFactory {

    @Override
    public IMouse createMouse() {
        return new LenovoMouse();
    }

    @Override
    public IKeyboard creatKeyboard() {
        return new LenovoKeyboard();
    }


}
