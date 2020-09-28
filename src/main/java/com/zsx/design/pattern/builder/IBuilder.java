package com.zsx.design.pattern.builder;

public interface IBuilder {
    void buildWheel();

    void buildSkeleton();

    void buildEngine();

    Product buildProduct();
}
