package com.roman.kleimenov.lab2.impl;

import com.roman.kleimenov.lab2.MyList;

import java.util.Arrays;

class MyLinkedList implements MyList {
    private Node first;
    private Node last;
    private int size;


    private static class Node {
        private Object value;
        private Node next;
        private Node prev;

        Node(Object value, Node prev, Node next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public void add(Object element) {
        Node lastNode = last;
        Node newNode = new Node(element, lastNode, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    public void add(int index, Object element) {
        if (index == size) {
            Node lastNode = last;
            Node newNode = new Node(element, lastNode, null);
            last = newNode;
            if (lastNode == null)
                first = newNode;
            else
                lastNode.next = newNode;
            size++;
        } else {
            Node currentNode = getNode(index);
            final Node previous = currentNode.prev;
            final Node newNode = new Node(element, previous, currentNode);
            currentNode.prev = newNode;
            if (previous == null)
                first = newNode;
            else
                previous.next = newNode;
            size++;
        }
    }

    public void addAll(Object[] elements) {
        Arrays.asList(elements).forEach(this::add);
    }

    public void addAll(int index, Object[] elements) {
        for (Object element : elements) {
            add(index++, element);
        }
    }

    public Object get(int index) {
        return getNode(index).value;
    }

    private Node getNode(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node current = first;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current;
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    public Object remove(int index) {
        Node el = getNode(index);
        Object element = el.value;
        Node next = el.next;
        Node prev = el.prev;

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

    public void set(int index, Object element) {
        Node node = getNode(index);
        node.value = element;
    }

    public int indexOf(Object value) {
        int index = 0;
        if (value == null) {
            throw new IllegalArgumentException("MyLinkedList is not supported null values");
        }
        for (Node x = first; x != null; x = x.next) {
            if (value.equals(x.value))
                return index;
            index++;
        }

        return -1;
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node x = first; x != null; x = x.next)
            array[i++] = x.value;
        return array;
    }
}
