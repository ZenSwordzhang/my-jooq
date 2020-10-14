package com.zsx.design.pattern.behavioral.iterator;

public interface Container<T> {

    Iterator<T> iterator();

    void add(T t);
}
