package com.nixsolutions.project8;

import interfaces.task8.CyclicItem;

import java.io.NotSerializableException;
import java.io.Serializable;

/**
 * Created by annnikon on 10.02.17.
 */
public class CyclicItemImpl implements CyclicItem, Serializable
{
    private Object value;
    private CyclicItem nextItem;
    private transient Object temp;

    //after creation nextItem for this is itself
    public CyclicItemImpl() {
        this.nextItem = this;
    }

    @Override
    public void setValue(Object o) {
        if (!(o instanceof Serializable)){
            NotSerializableException cause = new NotSerializableException(o.getClass().toString());
            throw new RuntimeException("Try to set not serializable object value: ", cause);
        }
        this.value = o;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setTemp(Object o) {
        this.temp = o;
    }

    @Override
    public Object getTemp() {
        return temp;
    }

    @Override
    public CyclicItem nextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(CyclicItem cyclicItem) {
        if (!(cyclicItem instanceof Serializable)) {
            NotSerializableException cause = new NotSerializableException(cyclicItem.getClass().toString());
            throw new RuntimeException("Try to set not serializable object value: ", cause);

        }
        this.nextItem = cyclicItem;
    }
}
