package com.zsx.design.pattern.builder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Director {

    private IBuilder builder;

    public Product buildProduct() {
        builder.buildEngine();
        builder.buildSkeleton();
        builder.buildWheel();
        return builder.buildProduct();
    }
}
