package com.roman.kleimenov.lab2.impl;

import com.roman.kleimenov.lab2.MyList;

import java.util.Arrays;

public class MyArrayList implements MyList {

    private static final int DEFAULT_SIZE = 5;
    private Object[] data;
    private int size;

    public MyArrayList() {
        this.data = new Object[DEFAULT_SIZE];
    }

    @Override
    public void add(Object element) {
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
    public void add(int index, Object element) {
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
    public void addAll(Object[] elements) {
        int inputSize = elements.length;
        checkCapacity(size + inputSize);
        System.arraycopy(elements, 0, data, size, inputSize);
        size += inputSize;
    }

    @Override
    public void addAll(int index, Object[] elements) {
        checkIndex(index);
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
    public Object get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object valueToDelete = get(index);
        int step = 1;
        int numberOfMovedValues = size - index - step;
        if (numberOfMovedValues > 0) {
            System.arraycopy(data, index + step, data, index, numberOfMovedValues);
        }
        data[--size] = null;
        return valueToDelete;
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        checkForNull(element);
        data[index] = element;
    }

    @Override
    public int indexOf(Object element) {
        checkForNull(element);
        for (int i = 0; i < data.length; i++) {
            if (element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    private void checkForNull(Object o) {
        if (o == null) throw new IllegalArgumentException("Object can't be null!");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }
}
