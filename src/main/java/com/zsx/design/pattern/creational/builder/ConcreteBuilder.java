package com.zsx.design.pattern.creational.builder;

public class ConcreteBuilder implements IBuilder {

    private Product product;

    public ConcreteBuilder() {
        this.product = new Product();
    }

    @Override
    public void buildWheel() {
        product.setWheel("Wheel");
    }

    @Override
    public void buildSkeleton() {
        product.setSkeleton("Skeleton");
    }

    @Override
    public void buildEngine() {
        product.setEngine("Engine");
    }

    @Override
    public Product buildProduct() {
        return product;
    }
}
