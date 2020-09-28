package com.zsx.design.pattern.abstraction.factory;

public class LenovoKeyboard implements IKeyboard {

    @Override
    public String getKeyboard() {
        System.out.println("Produce Lenovo keyboard");
        return "Lenovo keyboard";
    }

}
