package com.zsx.design.pattern.behavioral.strategy;

public class Context {

    //持有一个具体策略的对象
    private final Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public String contextInterface() {
        return strategy.strategyInterface();
    }
}
