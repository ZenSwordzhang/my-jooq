package com.zsx.design.pattern.creational.abstraction.factory;

public class LenovoKeyboard implements IKeyboard {

    @Override
    public String getKeyboard() {
        System.out.println("Produce Lenovo keyboard");
        return "Lenovo keyboard";
    }

}
