package com.nixsolutions.project5;

import interfaces.task5.ArrayCollection;
import interfaces.task5.ArrayIterator;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by annnikon on 30.01.17.
 */
public class ArrayListImpl<E> implements ArrayCollection<E> {
    public static final int INITIAL_CAPACITY = 10;
    public static final int MAX_CAPACITY = Integer.MAX_VALUE;
    public static final double GROW_SIZE = 2;

    private E[] array;
    private int elementsCount;

    /**
     * Default constructor
     */
    public ArrayListImpl() {
        array = (E[]) new Object[INITIAL_CAPACITY];
        elementsCount = 0;
    }

    /**
     * Constructor with user`s capacity
     */
    public ArrayListImpl(int capacity) {
        int c = (capacity > INITIAL_CAPACITY) ? capacity : INITIAL_CAPACITY;
        array = (E[]) new Object[c];
        elementsCount = 0;
    }

    @Override
    public void setArray(E[] input) {
        if (input == null) {
            throw new NullPointerException("Null array given.");
        }
        this.array = input;
        this.elementsCount = input.length;
    }

    /**
     * User should know only about array he puted into,
     * without empty cells created for ensuring capacity
     */
    @Override
    public Object[] getArray() {
        if (elementsCount < 1) {
            return (E[]) new Object[0];
        }

        E[] result = (E[]) new Object[this.elementsCount];
        System.arraycopy(array, 0, result, 0, elementsCount);
        return result;
    }


    @Override
    public boolean add(E e) {
        growArray();
        array[elementsCount++] = e;
        return true;
    }


    public E get(int index) {
        if (index >= elementsCount || index < 0) {
            throw new IllegalArgumentException("Wrong index");
        }
        return (E) array[index];
    }

    /**
     * Ensures if it is enough space to insert one more element,
     * and grows capacity at 1.5 times if not
     */
    public void growArray() {
        if (elementsCount == array.length) {
            int recommendedCapacity = (int) (array.length * GROW_SIZE);
            int newCapacity = (recommendedCapacity < MAX_CAPACITY) ?
                    recommendedCapacity : MAX_CAPACITY;
            E[] arrayCopy = (E[]) new Object[newCapacity];
            System.arraycopy(array, 0, arrayCopy, 0, elementsCount);
            this.array = arrayCopy;

        }
    }

    /**
     * Will change capacity if array has too many empty spaces.
     */
    public void compressArray() {

        int recommendedCapacity = (int) (array.length / GROW_SIZE);
        if (array.length > INITIAL_CAPACITY
                && elementsCount < recommendedCapacity) {
            E[] arrayCopy = (E[]) new Object[recommendedCapacity];
            System.arraycopy(array, 0, arrayCopy, 0, elementsCount);
            this.array = arrayCopy;
        }
    }

    @Override
    public int size() {
        return (elementsCount < MAX_CAPACITY) ? elementsCount : MAX_CAPACITY;
    }

    @Override
    public boolean isEmpty() {
        return elementsCount == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < elementsCount; i++) {
            if (array[i] == null) {
                result.append("null");
            } else {
                result.append(array[i]);
            }
            if (i != elementsCount - 1) {
                result.append(", ");
            }

        }
        result.append("}");
        return result.toString();
    }

    @Override
    public boolean contains(Object object) {
        if (elementsCount < 1) {
            return false;
        }
        return getFirstIndex(object) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        ArrayIteratorImpl iterator = this.new ArrayIteratorImpl();
        return iterator;
    }

    /**
     * Inner class which implements ArrayIterator
     */
    public class ArrayIteratorImpl implements ArrayIterator {
        private int nextIndex = 0;

        public Object[] getArray() {
            return ArrayListImpl.this.getArray();
        }

        public boolean hasNext() {
            return nextIndex < elementsCount;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            return (E) array[nextIndex++];
        }

        @Override
        public void remove() {
            if (nextIndex <= 0) {
                throw new IllegalStateException("Cannot remove before first next() method call");
            }
            nextIndex--;
            System.out.println("Index of element for removing:" + nextIndex);
            ArrayListImpl.this.remove(next());

        }
    }

    @Override
    public Object[] toArray() {
        return getArray();
    }

    /**
     * Returns an array containing all of the elements in this collection;
     * the runtime type of the returned array is that of the specified array.
     * If the collection fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this collection.
     */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Null array given.");
        }

        if (!a.getClass().equals(array.getClass())) {
            throw new ArrayStoreException("Incomparable type of array given. ");
        }

        return (T[]) Arrays.copyOf(array, elementsCount, a.getClass());
    }

    /**
     * Finds the first index of object in list. If object is not found,
     * will return -1.
     */
    public int getFirstIndex(Object o) {
        for (int i = 0; i < elementsCount; i++) {
            if (o == null && array[i] == null) {
                return i;
            } else {
                if (o != null && array[i] != null && o.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present.
     * Ensures capacity and compresses it if it`s too big.
     * Minuses 1 from elements count.
     */
    @Override
    public boolean remove(Object o) {
        int index = getFirstIndex(o);

        if (index == -1) {
            return false;
        }
        for (int i = index; i < elementsCount - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--elementsCount] = null;
        compressArray();
        return true;
    }

    /**
     * Returns true only if this collection contains all of the elements
     * in the specified collection.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Null collection is given");
        }
        if (c.size() > 0) {
            for (Object element : c) {
                if (!contains(element)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this collection.
     * Returns true if all the elements of collection were added.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        //checking only for references
        if (c == this) {
            throw new IllegalArgumentException("Cannot add myself. ");
        }

        for (E element : c) {
            if (!add(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).
     * Returns true if at least 1 object was removed
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Null collection given");
        }
        boolean collectionChanged = false;
        if (c.size() > 0) {
            for (Object element : c) {
                int index = getFirstIndex(element);
                if (index != -1 && remove(array[index])) {
                    collectionChanged = true;
                }
            }
        }
        return collectionChanged;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    /**
     * Removes from this collection all of its elements that are not contained in the
     * specified collection.
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Null collection given");
        }
        boolean collectionChanged = false;
        if (c.size() > 0) {
            for (int i = 0; i < elementsCount; i++) {
                //leave only elements which are present in c
                if (!c.contains(array[i])) {
                    collectionChanged = remove(array[i]);
                }
            }
        }
        return collectionChanged;

    }

    /**
     * Removes all of the elements from this collection (optional operation).
     * The collection will be empty after this method returns.
     */
    @Override
    public void clear() {
        this.array = (E[]) new Object[INITIAL_CAPACITY];
        this.elementsCount = 0;

    }


    @Override
    public Spliterator<E> spliterator() {
        return null;
    }


    @Override
    public Stream<E> stream() {
        return null;
    }


    @Override
    public Stream<E> parallelStream() {
        return null;
    }
}
