package com.zsx.design.pattern.creational.abstraction.factory;


public class LenovoMouse implements IMouse {

    @Override
    public String getMouse() {
        System.out.println("Produce Lenovo mouse");
        return "Lenovo mouse";
    }
}
