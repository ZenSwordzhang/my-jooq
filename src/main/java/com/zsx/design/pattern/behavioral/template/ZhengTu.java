package com.zsx.design.pattern.behavioral.template;

public class ZhengTu extends Game {

    @Override
    void initialize() {
        System.out.println("Initialize ZhengTu");
    }

    @Override
    void startPlay() {
        System.out.println("StartPlay ZhengTu");
    }

    @Override
    String endPlay() {
        System.out.println("EndPlay ZhengTu");
        return "ZhengTu";
    }
}
