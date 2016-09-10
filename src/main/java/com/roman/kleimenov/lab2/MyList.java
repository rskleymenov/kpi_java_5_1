package com.roman.kleimenov.lab2;

public interface MyList<E> {
    void add(E element);
    void add(int index, E element);
    void addAll(MyList<? extends E> elements);
    void addAll(int index, MyList<? extends E> elements);
    E get(int index);
    E remove(int index);
    void set(int index, E element);
    int indexOf(E o);
    int size();
    <E> E[] toArray();
}
