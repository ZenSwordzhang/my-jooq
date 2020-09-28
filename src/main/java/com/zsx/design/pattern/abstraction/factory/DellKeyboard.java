package com.zsx.design.pattern.abstraction.factory;


public class DellKeyboard implements IKeyboard {

    @Override
    public String getKeyboard() {
        System.out.println("Produce Dell keyboard");
        return "Dell keyboard";
    }
}
