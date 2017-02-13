package com.nixsolutions.project7.executor;

import interfaces.task7.executor.SumTask;

import java.math.BigInteger;

import java.util.Random;

/**
 * Created by annnikon on 06.02.17.
 */
public class SumTaskImpl implements SumTask {
    private static int initialId=0;
    private int id=initialId++;
    private int tryCount = 0;
    private long max; //minimum value of 0 and a maximum value of 2^64-1
    private int numBits;
    private BigInteger result;
    private int count = 0;

    @Override
    public void setCount(int i) {
        if (i>=0) {
            this.count = i;
        }
    }

    @Override
    public void setMax(long value) {
        if (value<1) {
            throw new IllegalArgumentException("Value shouldn`t be less than 1. ");
        }
        this.max = value;
        this.numBits = getPowOf2(value);

    }

    /**Returns number of bits needed to write given value. Used for constructing BigInteger*/
    private int getPowOf2(long value) {

        double x = new Long(value).doubleValue();
        double log = Math.log(x) / Math.log(2);
        return (int) log;

    }

    /**Returns a number between 0 and 2^numBits - 1*/
    @Override
    public BigInteger getRandom() {
        BigInteger number = new BigInteger(numBits, new Random());

        return number;
    }

    @Override
    public BigInteger getResult() {
        return result;
    }

    @Override
    public int getTryCount() {
        return tryCount;
    }

    @Override
    public void incTryCount() {

            tryCount++;

    }

    @Override
    public boolean execute() throws Exception {
        result = BigInteger.ZERO;
        for (int i=0; i<count; i++) {
            BigInteger nextComponent = getRandom();
            if (nextComponent.longValue()>max) {
                System.out.println("\tResult of sum task #"+id+" : wrong value generated");
                return false;
            }
            result = result.add(nextComponent);

        }
        System.out.println("\tResult of sum task #"+id+" : ok. Sum = " + result);
        return true;
    }




}
