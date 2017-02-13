package com.nixsolutions.project8;

import interfaces.task8.CyclicCollection;
import interfaces.task8.CyclicItem;

import java.io.NotSerializableException;
import java.io.Serializable;

/**
 * Created by annnikon on 10.02.17.
 */
public class CyclicCollectionImpl implements CyclicCollection, Serializable {

   private int count;
   private CyclicItem first;


    @Override
    public boolean add(CyclicItem cyclicItem) {
        if (cyclicItem == null) {
            throw  new NullPointerException("Null item is given");
        }
        if (!(cyclicItem instanceof Serializable)){
            NotSerializableException cause = new NotSerializableException(cyclicItem.getClass().toString());
            throw new RuntimeException("Try to set not serializable item: ", cause);
        }
         //insert first element
        if (count == 0) {
            this.first = cyclicItem;
            cyclicItem.setNextItem(this.first);
            count++;
        }

        //this collection already has elements
        else {
            int indexOfItem = getIndexOf(cyclicItem);
            if (indexOfItem != -1) {
                throw new IllegalArgumentException("This element is already in collection in index: "
                        +indexOfItem);
            }
            CyclicItem last = getByIndex(count-1);
            last.setNextItem(cyclicItem);
            cyclicItem.setNextItem(this.first);
            count++;
        }

        return true;
    }

    //returns index of this item in collection, and -1, if it doesnt contain element
    private int getIndexOf (CyclicItem item) {
        CyclicItem current = first;
        for (int i = 0; i<count; i++) {
            if (item.equals(current)) {
                return i;
            }
            current = current.nextItem();

        }
        return -1;
    }

    //goes through collection
    private CyclicItem getByIndex(int index) {
        if (index<0 || index>=count) {
            return null;
        }
        CyclicItem current = first;
        for(int i=0;i<index;i++) {
            current = current.nextItem();
        }
        return current;

    }



    @Override
    public void insertAfter(CyclicItem item, CyclicItem newItem) {
        if (item==null || newItem == null) {
            throw new NullPointerException("Null item given. ");
        }
        if (count == 0) {
            throw new IllegalArgumentException("Empty collection. ");
        }
        int indexOfItem = getIndexOf(item);
        int indexOfNewItem = getIndexOf(newItem);
        if (indexOfItem == -1) {
            throw new IllegalArgumentException("Item is not in collection.");
        }
        if (indexOfNewItem != -1) {
            throw new IllegalArgumentException("Cannot add duplicate of newItem. ");
        }
        CyclicItem afterItem  = item.nextItem();
        item.setNextItem(newItem);
        newItem.setNextItem(afterItem);
        count++;


    }

    @Override
    public CyclicItem getFirst() {
        return first;
    }

    @Override
    public boolean remove(CyclicItem cyclicItem) {
        if (cyclicItem == null) {
            throw new NullPointerException("Null item given. ");
        }
        if (count < 1) {
            System.out.println("Collection is empty. ");
            return false;
        }
        int index = getIndexOf(cyclicItem);
        if (index == -1 ) {
            System.out.println("Collection doesnt contain item. ");
            return false;
        }

        //if it is the only element in collection
        if (cyclicItem.equals(first) && count==1) {
            this.first = null;
        }
        //removing head of collection. Now head is second
        else if (cyclicItem.equals(first)&&count>1) {
            CyclicItem second = first.nextItem();
            CyclicItem last = getByIndex(count-1);
            last.setNextItem(second); //now last item links the first
            this.first = second;
        }
        //wrevious now will link to next
        else {
            CyclicItem previous = getByIndex(index-1);
            CyclicItem next = cyclicItem.nextItem();
            previous.setNextItem(next);
        }
        count--;
        cyclicItem.setNextItem(cyclicItem); //cut it from list
        return true;

    }



    @Override
    public int size() {
        return count;
    }
}
