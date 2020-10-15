package com.zsx.design.pattern.behavioral.observer;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public List<String> changeState() {
        return notifyAllObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private List<String> notifyAllObservers() {
        List<String> list = Lists.newArrayList();
        for (Observer observer : observers) {
            list.add(observer.update());
        }
        return list;
    }
}
