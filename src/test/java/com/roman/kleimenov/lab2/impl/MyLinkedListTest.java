package com.roman.kleimenov.lab2.impl;

import com.roman.kleimenov.lab2.MyList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MyLinkedListTest {

    private MyList list;
    private static final int DEFAULT_SIZE = 6;

    @Before
    public void init() {
        list = new MyLinkedList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
    }

    @Test
    public void linkedListShouldAddTwoItems() {
        assertEquals(DEFAULT_SIZE, list.size());
    }

    @Test
    public void linkedListShouldGetItemByIndex() {
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void linkedListShouldThrowExceptionDueToInvalidIndex() {
        list.get(Integer.MAX_VALUE);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void linkedListShouldThrowExceptionDueToInvalidIndex2() {
        list.get(Integer.MIN_VALUE);
    }

    @Test
    public void removeShouldReturnRemovedValue() {
        assertEquals("1", list.remove(0));
    }

    @Test
    public void afterRemoveSizeShouldLessenByOne() {
        list.remove(1);
        assertEquals(DEFAULT_SIZE - 1, list.size());
    }

    @Test
    public void whenFirstElementWillBeDeletedSecondElementShouldChangeStayOnTheFirstPosition() {
        assertEquals("2", list.remove(1));
        assertEquals("3", list.get(1));
    }

    @Test
    public void shouldChangeValueByIndex() {
        String newValue = "something";
        int defaultIndex = 1;
        list.set(defaultIndex, newValue);
        assertEquals(newValue, list.get(defaultIndex));
    }

    @Test(expected = IllegalArgumentException.class)
    public void indexOfShouldRiseExceptionDueToNullValue() {
        list.indexOf(null);
    }

    @Test
    public void indexOfShuldReturnExpectedValue() {
        assertEquals(0, list.indexOf("1"));
        assertEquals(5, list.indexOf("6"));
    }

    @Test
    public void shouldConvertToArray() {
        assertArrayEquals(new Object[]{"1", "2", "3", "4", "5", "6"}, list.toArray());
    }

    @Test
    public void shouldInsertIntoFirstPosition() {
        String newValue = "newValue";
        int index = 2;
        list.add(index, newValue);
        assertEquals(newValue, list.get(index));
    }

    @Test
    public void shouldAddAllItems() {
        list.addAll(new Object[]{"7", "8", "9"});
        assertEquals(DEFAULT_SIZE + 3, list.size());
        assertEquals("7", list.get(6));
        assertEquals("8", list.get(7));
        assertEquals("9", list.get(8));
    }

    @Test
    public void shouldAddAllItemsToIndex() {
        list.addAll(1, new Object[]{"7", "8", "9"});
        assertEquals(DEFAULT_SIZE + 3, list.size());
        assertEquals("7", list.get(1));
        assertEquals("8", list.get(2));
        assertEquals("9", list.get(3));
    }
}