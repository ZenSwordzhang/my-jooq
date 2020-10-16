package com.zsx.design.pattern.behavioral.state;

public class Document {

    private State state;

    public void render() {
        state.render();
    }

    public String publish() {
        return state.publish();
    }

    public void changeState(State state) {
        this.state = state;
    }
}
