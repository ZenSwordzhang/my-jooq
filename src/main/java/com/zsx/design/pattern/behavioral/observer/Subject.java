package com.zsx.design.pattern.behavioral.observer;


import java.util.List;

public interface Subject {

    List<String> changeState();

    void addObserver(Observer observer);

    void removeObserver(Observer observer);
}
