package com.zsx.design.pattern.behavioral.template;

public class LOL extends Game {

    @Override
    void initialize() {
        System.out.println("Initialize LOL");
    }

    @Override
    void startPlay() {
        System.out.println("StartPlay LOL");
    }

    @Override
    String endPlay() {
        System.out.println("EndPlay LOL");
        return "LOL";
    }
}
