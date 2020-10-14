package com.zsx.design.pattern.behavioral.iterator;

public class ConcreteContainer<E> implements Container<E> {

    private final Object[] elements;

    private int size = 0;

    public ConcreteContainer() {
        elements = new Object[10];
    }

    @Override
    public Iterator<E> iterator() {
        return new ConcreteIterator();
    }

    @Override
    public void add(E e) {
        elements[size++] = e;
    }

    private class ConcreteIterator implements Iterator<E> {

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (this.hasNext()) {
                return (E)elements[cursor++];
            }
            return null;
        }
    }
}
