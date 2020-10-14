package com.zsx.design.pattern.behavioral.template;

public abstract class Game {

    abstract void initialize();

    abstract void startPlay();

    abstract String endPlay();

    public final String play() {

        initialize();

        startPlay();

        return endPlay();
    }
}
