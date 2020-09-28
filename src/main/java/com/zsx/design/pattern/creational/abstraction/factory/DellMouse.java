package com.zsx.design.pattern.creational.abstraction.factory;


public class DellMouse implements IMouse {

    @Override
    public String getMouse() {
        System.out.println("Produce Dell mouse");
        return "Dell mouse";
    }

}
