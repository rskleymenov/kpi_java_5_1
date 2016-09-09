package com.roman.kleimenov.lab2;

public interface MyList {
    void add(Object element);
    void add(int index, Object element);
    void addAll(Object[] elements);
    void addAll(int index, Object[] elements);
    Object get(int index);
    Object remove(int index);
    void set(int index, Object element);
    int indexOf(Object o);
    int size();
    Object[] toArray();
}
