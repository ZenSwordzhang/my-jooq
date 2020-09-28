package com.zsx.design.pattern.creational.builder;

public interface IBuilder {
    void buildWheel();

    void buildSkeleton();

    void buildEngine();

    Product buildProduct();
}
