package com.roman.kleimenov.lab2.impl;

import com.roman.kleimenov.lab2.MyList;

import java.util.Arrays;

public class MyLinkedList<E> implements MyList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(E value, Node<E> prev, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(E element) {
        Node<E> lastNode = last;
        Node<E> newNode = new Node<>(element, lastNode, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            Node<E> lastNode = last;
            Node<E> newNode = new Node<>(element, lastNode, null);
            last = newNode;
            if (lastNode == null)
                first = newNode;
            else
                lastNode.next = newNode;
            size++;
        } else {
            Node<E> currentNode = getNode(index);
            final Node<E> previous = currentNode.prev;
            final Node<E> newNode = new Node<>(element, previous, currentNode);
            currentNode.prev = newNode;
            if (previous == null)
                first = newNode;
            else
                previous.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(MyList<? extends E> elements) {
        Arrays.asList(elements.toArray()).forEach(e -> this.add((E) e));
    }

    @Override
    public void addAll(int index, MyList<? extends E> elements) {
        for (Object element : elements.toArray()) {
            add(index++, (E) element);
        }
    }

    @Override
    public E get(int index) {
        return getNode(index).value;
    }

    private Node<E> getNode(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<E> current = first;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current;
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    @Override
    public E remove(int index) {
        Node<E> el = getNode(index);
        E element = el.value;
        Node<E> next = el.next;
        Node<E> prev = el.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            el.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            el.next = null;
        }

        el.value = null;
        size--;
        return element;
    }

    @Override
    public void set(int index, E element) {
        Node<E> node = getNode(index);
        node.value = element;
    }

    @Override
    public int indexOf(E value) {
        int index = 0;
        if (value == null) {
            throw new IllegalArgumentException("MyLinkedList is not supported null values");
        }
        for (Node<E> x = first; x != null; x = x.next) {
            if (value.equals(x.value))
                return index;
            index++;
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public <E> E[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node x = first; x != null; x = x.next)
            array[i++] = x.value;
        return (E[]) array;
    }
}
