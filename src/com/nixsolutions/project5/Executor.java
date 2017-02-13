package com.nixsolutions.project5;

import interfaces.task5.ArrayIterator;

import java.util.*;

/**
 * Created by annnikon on 31.01.17.
 */
public class Executor {
    public static void testSetAndGet() {
        ArrayListImpl<Double> list = new ArrayListImpl<Double>();
        System.out.println("List created: " + list);
        System.out.println("After creation getArray() is not null: " + (list.getArray() != null)); //true
        Double[] arrayEmpty = {};
        list.setArray(arrayEmpty);
        System.out.println("After setting empty array: " + list);
        System.out.println("getArray() length: " + list.getArray().length);
        Double[] arrayWithNull = {null};
        list.setArray(arrayWithNull);
        System.out.println("After setting array with 1 value (null): " + list);
        System.out.println("getArray() length: " + list.getArray().length);
        Double[] usualArray = {1d, 2d, null, 3d};
        list.setArray(usualArray);
        System.out.println("After setting array with 4 values (1 is null): " + list);
        System.out.println("getArray() length: " + list.getArray().length);
        System.out.println("Boxed and unboxed arrays are equal: "
                + Arrays.equals(list.getArray(), usualArray)); //should be true
        try {
            System.out.print("Setting null to array: ");
            list.setArray(null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void testAddAndRemove() {

        ArrayListImpl<Double> list = new ArrayListImpl<>();
        System.out.println("\nList created: " + list);
        list.add(null);
        System.out.println("Added null to list: " + list);
        System.out.println("getArray() length: " + list.getArray().length); //1
        Double lastElement = (Double) list.getArray()[list.getArray().length - 1];
        System.out.println("Last element is null: " + (lastElement == null));
        list.add(list.get(0));
        System.out.println("Added duplicate to list: " + list);
        ArrayList<Double> collection1 = new ArrayList<Double>();
        collection1.add(null);
        collection1.add(6d);
        collection1.add(7d);
        list.addAll(collection1);
        System.out.println("Added collection1 to list: " + list);
        Collection<Double> collection2 = Arrays.asList(6d, 0d, null, 9d);
        list.addAll(collection2);
        System.out.println("Added collection2 to list: " + list);

        System.out.println("getArray() length: " + list.getArray().length);
        System.out.println("Removed null: " + list.remove(null));
        System.out.println("Now list is: " + list);
        Collection<Double> collection = Arrays.asList(null, 6d, 7d);
        System.out.println("Removed collection {null,6,7} :" + list.removeAll(collection));
        System.out.println("Now list is: " + list);
        System.out.println("Removed collection {null,6,7} one more time:"
                + list.removeAll(collection));
        System.out.println("Now list is: " + list);
        System.out.println("Removed unexisting element: " + list.remove(3.4d));
        System.out.println("List haven`t changed: " + list);
        System.out.print("Add list to list: ");
        try {
            list.addAll(list);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void testClear() {
        ArrayListImpl<Character> list = new ArrayListImpl<>();
        System.out.println("\nNew Character list created: " + list);
        System.out.println("List is empty: " + list.isEmpty());
        System.out.println("List size: " + list.size());
        list.addAll(Arrays.asList('a', 'b', null, 'c'));
        System.out.println("After adding: " + list);
        System.out.println("List is empty: " + list.isEmpty());
        System.out.println("List size: " + list.size());
        Object[] unboxed = list.getArray();
        System.out.println("getArray() length: " + unboxed.length);
        list.clear();
        System.out.println("List cleared: " + list.isEmpty());
        System.out.println("List size: " + list.size());
        Object[] unboxed2 = list.getArray();
        System.out.println("getArray() length: " + unboxed2.length); //0 (empty)

    }

    public static void testContains() {
        ArrayListImpl<String> list = new ArrayListImpl<>();
        System.out.println("\nNew String list created: " + list);
        System.out.println("New list contains null: " + list.contains(null));
        System.out.println("Added null to list: " + list.add(null));
        System.out.println("Now list is: " + list);
        System.out.println("Now list contains null: " + list.contains(null));
        System.out.println("Added empty string to list: " + list.add(new String()));
        System.out.println("Now list is: " + list);
        System.out.println("This list contains empty string: " + list.contains(""));
        System.out.println("Added 'abc' string to list: " + list.add("abc"));
        System.out.println("Now list is: " + list);
        System.out.println("List contains 'ab'+'c': " + list.contains("ab" + "c"));
        Collection<String> collection1 = Arrays.asList("", null, "abc");
        Collection<String> collection2 = Arrays.asList("", "abc", "abcd");
        System.out.println("List contains all collection1: " + list.containsAll(collection1));
        System.out.println("List contains all collection2: " + list.containsAll(collection2));
        //System.out.println("List contains all itself: " +list.containsAll(list));
        System.out.print("List contains all null:");
        try {
            System.out.println(list.containsAll(null));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        list.clear();
        System.out.println("Cleared list contains null: " + list.contains(null));

    }

    //retain this collection with c is the same that intersection which allows duplicate
    public static void testRetain() {
        ArrayListImpl<Integer> list = new ArrayListImpl<>();
        System.out.println("\nNew Integer list created: " + list);
        Collection<Integer> collection1 = Arrays.asList(1, 2, 3, 4);
        Collection<Integer> collection2 = Arrays.asList(2, 2, 3, 0);
        list.addAll(collection1);
        System.out.println("List after adding all collection1: " + list);
        System.out.println("List changed after retaining collection1: " + list.retainAll(collection1));
        System.out.println("List: " + list);
        System.out.println("List changed after retaining collection2: " + list.retainAll(collection2));
        System.out.println("List: " + list);

    }

    public static void printArray(Object[] array) {
        System.out.print("[ ");
        for (Object element : array) {
            if (element == null) {
                System.out.print("null ");
            } else {
                System.out.print(element + " ");
            }
        }
        System.out.println("]");
    }

    public static void testToArray() {
        ArrayListImpl<Integer> list = new ArrayListImpl<>();
        System.out.println("\nNew Integer list created: " + list);
        Integer[] intArray = {1, 2, null, 4, 5};
        list.setArray(intArray);
        System.out.println("Seted list: " + list);
        Object[] unboxed1 = list.toArray();
        Integer[] unboxed2 = list.toArray(new Integer[0]);
        System.out.print("Elements from toArray() : ");
        printArray(unboxed1);
        System.out.println("Arrays are equal: " + Arrays.equals(intArray, unboxed1));
        System.out.print("Elements from toArray(Integer[]) : ");
        printArray(unboxed2);
        System.out.println("Arrays are equal: " + Arrays.equals(intArray, unboxed2));
        System.out.print("Try to get array as String[] : ");
        try {
            String[] stringArray = list.toArray(new String[0]);
            System.out.println(stringArray);
        } catch (ArrayStoreException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void testIterator() {
        ArrayListImpl<Double> list = new ArrayListImpl<>();
        ArrayIterator<Double> iterator = (ArrayIterator<Double>) list.iterator();
        System.out.print("\nFor empty list iterator.getArray() :");
        printArray(iterator.getArray());
        System.out.println("Has next: " + iterator.hasNext());
        list.setArray(new Double[]{1d, 2d, 3d, null, 4d});
        System.out.println("\nNew Double list created: " + list);
        System.out.print("Try to remove: ");
        try {
            iterator.remove();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Iterator steps: ");
        int counter = 0;
        while (iterator.hasNext()) {
            Object element = iterator.next();
            System.out.print("Element at position " + counter++ + " : ");
            if (element == null) {
                System.out.println("null");
            } else {
                System.out.println(element);
            }

        }
        System.out.print("Get array :");
        printArray(iterator.getArray());
        if (!iterator.hasNext()) {
            System.out.println("Collection ended.");
        }

        ArrayIterator<Double> iterator2 = (ArrayIterator<Double>) list.iterator();
        System.out.println("New iterator for deleting first element:");
        iterator2.next();
        iterator2.remove();
        printArray(iterator2.getArray());
        System.out.println("Now list is: " + list);

    }


    public static void main(String[] args) {

        testSetAndGet();
        testAddAndRemove();
        testClear();
        testContains();
        testRetain();
        testToArray();
        testIterator();
    }
}
