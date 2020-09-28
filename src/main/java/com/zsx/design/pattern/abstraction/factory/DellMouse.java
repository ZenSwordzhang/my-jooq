package com.zsx.design.pattern.abstraction.factory;


public class DellMouse implements IMouse {

    @Override
    public String getMouse() {
        System.out.println("Produce Dell mouse");
        return "Dell mouse";
    }

}
