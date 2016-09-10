package com.roman.kleimenov.lab2.impl;

import com.roman.kleimenov.lab2.MyList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class MyArrayListTest {
    private static final MyList<Integer> ELEMENTS = new MyArrayList<>();
    static {
        ELEMENTS.add(444);
        ELEMENTS.add(555);
        ELEMENTS.add(666);
    }
    private MyList<Integer> list;
    private static final int SIZE = 50;

    @Before
    public void init() {
        list = new MyArrayList();
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    @Test
    public void shouldAddItemsToArrayList() {
        assertEquals(SIZE, list.size());
    }

    @Test
    public void shouldAddItemByIndex() {
        Integer expected = 234;
        Integer index = 25;
        list.add(index, expected);
        assertEquals(SIZE + 1, list.size());
        assertEquals(expected, list.get(index));
    }

    @Test
    public void shouldRemoveItemByIndex() {
        Integer value = 15;
        assertEquals(value, list.remove(value));
        assertEquals(SIZE - 1, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldRiseExceptionToNonPositiveIndex() {
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldRiseExceptionToIndexThatLargerThanSizeOfArray() {
        list.get(SIZE + 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldRiseExceptionToIndexThatLargerThanSizeOfArray2() {
        list.add(SIZE + 1, 15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void indexOfShouldRiseExceptionDueToNullValue() {
        list.indexOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addByIndexShouldRiseExceptionDueToNullValue() {
        list.add(5, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addShouldRiseExceptionDueToNullValue() {
        list.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setShouldRiseExceptionDueToNullValue() {
        list.set(5, null);
    }

    @Test
    public void indexOfShouldReturnIndexOfElement() {
        assertEquals(5, list.indexOf(5));
    }

    @Test
    public void indexOfShouldNotReturnIndexOfElement() {
        assertEquals(-1, list.indexOf(355));
    }

    @Test
    public void shouldReturnSizeOfArray() {
        assertEquals(SIZE, list.size());
    }

    @Test
    public void shouldReturnArray() {
        Object[] expected = new Object[SIZE];
        for (int i = 0; i < SIZE; i++) {
            expected[i] = i;
        }
        assertArrayEquals(expected, list.toArray());
    }

    @Test
    public void shouldAddAllWithoutIndex() {
        list.addAll(ELEMENTS);
        int size = list.size();
        assertEquals(Integer.valueOf(444), list.get(size - 3));
        assertEquals(Integer.valueOf(555), list.get(size - 2));
        assertEquals(Integer.valueOf(666), list.get(size - 1));
        assertEquals(SIZE + 3, size);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldRiseExceptionDueToIllegalIndexInAddAll() {
        list.addAll(SIZE + 10, ELEMENTS);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldRiseExceptionDueToIllegalIndexInAddAll2() {
        list.addAll(-1, ELEMENTS);
    }

    @Test
    public void shouldAddItemsByIndex() {
        list.addAll(SIZE / 2, ELEMENTS);
        assertEquals(SIZE + 3, list.size());
        assertEquals(ELEMENTS.get(0), list.get(SIZE / 2));
        assertEquals(ELEMENTS.get(1), list.get(SIZE / 2 + 1));
        assertEquals(ELEMENTS.get(2), list.get(SIZE / 2 + 2));
    }

    @Test
    public void shouldSetValue() {
        Integer index = 5;
        Integer element = 999;
        list.set(index, element);
        assertEquals(element, list.get(index));
    }

}