package com.zsx.design.pattern.behavioral.state;

public class Moderation implements State {

    private final Document document;

    public Moderation(Document document) {
        this.document = document;
    }

    @Override
    public void render() {
        System.out.println("Render Moderation");
    }

    @Override
    public String publish() {
        document.changeState(new Published(document));
        return "Moderation-publish";
    }
}
