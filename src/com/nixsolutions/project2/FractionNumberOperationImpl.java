package com.nixsolutions.project2;

import interfaces.task2.FractionNumber;
import interfaces.task2.FractionNumberOperation;

/**
 * Created by annnikon on 25.01.17.
 */
public class FractionNumberOperationImpl implements FractionNumberOperation {

    @Override
    public FractionNumber add(FractionNumber a, FractionNumber b) {
        int commonDivisor = Utils.minimalCommonDividend(a.getDivisor(),
                b.getDivisor());
        int coeficientA = commonDivisor / a.getDivisor();
        int coeficientB = commonDivisor / b.getDivisor();
        int newDivident = a.getDividend() * coeficientA +
                b.getDividend() * coeficientB;
        return new FractionNumberImpl(newDivident, commonDivisor);
    }

    @Override
    public FractionNumber sub(FractionNumber a, FractionNumber b) {
        FractionNumberImpl negativeB = new FractionNumberImpl(b.getDividend() *
                (-1), b.getDivisor());
        return add(a, negativeB);
    }

    @Override
    public FractionNumber mul(FractionNumber a, FractionNumber b) {
        int newDivident = a.getDividend() * b.getDividend();
        int newDivisor = a.getDivisor() * b.getDivisor();
        return new FractionNumberImpl(newDivident, newDivisor);
    }

    @Override
    public FractionNumber div(FractionNumber a, FractionNumber b) {
        if (b.getDividend() == 0) {
            throw new ArithmeticException("Divide by zero is not allowed");
        }
        return mul(a, getReversed(b));
    }


    public FractionNumber getReversed(FractionNumber a) {
        return new FractionNumberImpl(a.getDivisor(), a.getDividend());
    }
}
