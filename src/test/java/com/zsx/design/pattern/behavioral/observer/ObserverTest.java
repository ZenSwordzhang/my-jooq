package com.zsx.design.pattern.behavioral.observer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class ObserverTest {

    @Test
    void testChangeState() {
        Subject sub = new ConcreteSubject();
        Observer observerA = new ConcreteObserverA();
        sub.addObserver(observerA);
        assertLinesMatch(List.of("ConcreteObserverA"), sub.changeState());

        sub.addObserver(new ConcreteObserverB());
        assertLinesMatch(List.of("ConcreteObserverA", "ConcreteObserverB"), sub.changeState());

        sub.removeObserver(observerA);
        assertLinesMatch(List.of("ConcreteObserverB"), sub.changeState());
    }
}
