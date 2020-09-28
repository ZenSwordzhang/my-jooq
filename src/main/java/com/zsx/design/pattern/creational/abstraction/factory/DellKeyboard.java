package com.zsx.design.pattern.creational.abstraction.factory;


public class DellKeyboard implements IKeyboard {

    @Override
    public String getKeyboard() {
        System.out.println("Produce Dell keyboard");
        return "Dell keyboard";
    }
}
