package com.zsx.design.pattern.behavioral.state;

public class Published implements State {

    private Document document;

    public Published(Document document) {
        this.document = document;
    }

    @Override
    public void render() {
        System.out.println("Render Published");
    }

    @Override
    public String publish() {
        return "Published-publish";
    }
}
