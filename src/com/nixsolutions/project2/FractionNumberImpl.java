package com.nixsolutions.project2;

import interfaces.task2.FractionNumber;

/**
 * Class implementation for fractal numbers
 *
 * @author annnikon
 * @version 1.0
 */

public class FractionNumberImpl implements FractionNumber {

    public static final int DEFAULT_DIVIDENT = 1;
    public static final int DEFAULT_DIVISOR = 2;
    private int divident;
    private int divisor;

    public FractionNumberImpl(int divident, int divisor) {
        setDividend(divident);
        setDivisor(divisor);
        simplify();
        checkMinuse();
    }

    /**
     * Constructor for default
     */
    public FractionNumberImpl() {
        setDividend(DEFAULT_DIVIDENT);
        setDivisor(DEFAULT_DIVISOR);
    }

    @Override
    public int getDividend() {
        return divident;
    }


    @Override
    public int getDivisor() {
        return divisor;
    }


    @Override
    public void setDividend(int divident) {

        this.divident = divident;

    }

    @Override
    public void setDivisor(int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Cannot create fraction with zero divisor");
        }
        this.divisor = divisor;

    }


    @Override
    public String toStringValue() {
        if (divident == 0) {
            return "0";
        }

        return (divident + "/" + divisor);
    }

    @Override
    public double value() {

        return ((double) divident) / divisor;
    }

    /** There is a standart to write a minus near a dividend, not near a divisor.
     * According basic math rules, a/-b is the same number as -a/b*/
    private void checkMinuse() {
        if (divident >= 0 && divisor < 0) {
            divident *= -1;
            divisor *= -1;
        }
    }

    /**If fractal`s divisor and divident have a common divisor, the fractal will be
     * simplified by dividing both elements by this common divisor.
     */
    private void simplify() {
        int greatestCommonDivisor = Utils.greatestCommonDivisor(divident, divisor);
        if (greatestCommonDivisor == 1) {
            return;
        }
        setDividend(divident / greatestCommonDivisor);
        setDivisor(divisor / greatestCommonDivisor);
    }


}

