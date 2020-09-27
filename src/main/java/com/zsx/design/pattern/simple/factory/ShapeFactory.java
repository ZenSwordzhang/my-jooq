package com.zsx.design.pattern.simple.factory;

public class ShapeFactory {

    //根据传入的对象获取图形
    public IShape getShap(String shapeType) {

        shapeType = shapeType.toLowerCase();
        switch (shapeType) {
            case "square":
                return new Square();
            case "rectangle":
                return new Rectangle();
            case "circle":
                return new Circle();
            default:
                return null;
        }
    }
}