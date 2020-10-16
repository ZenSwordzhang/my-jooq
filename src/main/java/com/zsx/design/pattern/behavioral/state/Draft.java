package com.zsx.design.pattern.behavioral.state;

public class Draft implements State {

    private final Document document;

    public Draft(Document document) {
        this.document = document;
    }

    @Override
    public void render() {
        System.out.println("Render Draft");
    }

    @Override
    public String publish() {
        document.changeState(new Moderation(document));
        return "Draft-publish";
    }
}
