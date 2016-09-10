package com.roman.kleimenov.lab2.impl;

import com.roman.kleimenov.lab2.MyList;

import java.util.Arrays;

public class MyArrayList<E> implements MyList<E> {

    private static final int DEFAULT_SIZE = 5;
    private Object[] data;
    private int size;

    public MyArrayList() {
        this.data = new Object[DEFAULT_SIZE];
    }

    @Override
    public void add(E element) {
        checkCapacity(size + 1);
        checkForNull(element);
        data[size++] = element;
    }

    private void checkCapacity(int capacity) {
        if (capacity > data.length) {
            int oldCapacity = data.length;
            int newCapacity = oldCapacity * 3 / 2;
            if (newCapacity - Integer.MAX_VALUE > 0)
                newCapacity = Integer.MAX_VALUE;
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        checkCapacity(size + 1);
        checkForNull(element);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    private void checkIndex(int index) {
        if (index > size || index < 0 || data.length < index) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    @Override
    public void addAll(MyList<? extends E> el) {
        E[] elements = el.toArray();
        int inputSize = elements.length;
        checkCapacity(size + inputSize);
        System.arraycopy(elements, 0, data, size, inputSize);
        size += inputSize;
    }

    @Override
    public void addAll(int index, MyList<? extends E> el) {
        checkIndex(index);
        E[] elements = el.toArray();
        int inputSize = elements.length;
        checkCapacity(size + inputSize);
        int numberOfMovedValues = size - index;
        if (numberOfMovedValues > 0) {
            System.arraycopy(data, index, data, index + inputSize, numberOfMovedValues);
        }
        System.arraycopy(elements, 0, data, index, inputSize);
        size += inputSize;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Object valueToDelete = get(index);
        int step = 1;
        int numberOfMovedValues = size - index - step;
        if (numberOfMovedValues > 0) {
            System.arraycopy(data, index + step, data, index, numberOfMovedValues);
        }
        data[--size] = null;
        return (E) valueToDelete;
    }

    @Override
    public void set(int index, E element) {
        checkIndex(index);
        checkForNull(element);
        data[index] = element;
    }

    @Override
    public int indexOf(E element) {
        checkForNull(element);
        for (int i = 0; i < data.length; i++) {
            if (element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    private void checkForNull(E o) {
        if (o == null) throw new IllegalArgumentException("Object can't be null!");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public <E> E[] toArray() {
        return (E[]) Arrays.copyOf(data, size);
    }
}
