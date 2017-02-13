package com.nixsolutions.project3;

import java.math.BigDecimal;

/**
 * Class that describes a double number by its sign
 * (positive or negative),
 * int part (before separator),
 * fractal part (after separator),
 * and expotential part.
 * Created by annnikon on 26.01.17.
 */
public class DoubleNumber {

    private boolean isNegative;
    private double intPart;
    private double fractionPart;
    private double ePart;

    public DoubleNumber() {
        this.isNegative = false;
        this.intPart = 0;
        this.fractionPart = 0;
        this.ePart = 0;
    }

    public DoubleNumber(boolean isNegative,
                        String intString,
                        String fractionString,
                        String eString) {
        setNegative(isNegative);
        setIntPart(intString);
        setFractionPart(fractionString);
        setEPart(eString);
    }

    /**
     * Given string will be checked if it has non-numeric symbols.
     */
    public void setIntPart(String intString) {
        if (intString.length() == 0) {
            this.intPart = 0;
            return;
        }
        if (!NumberUtils.isStringValid(intString)) {
            InputException cause=new InputException(intString);
            throw new IllegalArgumentException("Cannot set int part.",cause);
        }
        this.intPart = countIntValue(intString);
    }

    /**
     * Given string will be checked if it has non-numeric symbols.
     */
    public void setFractionPart(String fractionString) {
        if (!NumberUtils.isStringValid(fractionString)) {
            InputException cause=new InputException(fractionString);
            throw new IllegalArgumentException("Cannot set fraction part.",cause);
        }
        this.fractionPart = countFractionValue(fractionString);
    }

    public void setEPart(String eString) {
        if (eString == null || eString.length() == 0) {
            throw new IllegalArgumentException("Empty expotential part given.");
        }
        int coeficient = 1;

        if (eString.startsWith("-")) {
            coeficient = -1;
        } else if (eString.startsWith("+")) {
            coeficient = 1;
        } else if (eString.startsWith("0")) {
            this.ePart = 0;
            return;
        } else {
            InputException cause=new InputException(eString);
            throw new IllegalArgumentException("Expotential part should start " +
                    "with '+', '-', or '0'.", cause);
        }
        String analyzedString = eString.substring(1);
        if (!NumberUtils.isStringValid(analyzedString)) {
            InputException cause=new InputException(analyzedString);
            throw new IllegalArgumentException("Cannot set expotential part. " + cause);
        }
        this.ePart = countIntValue(analyzedString) * coeficient;
    }


    public void setNegative(boolean isNegative) {
        this.isNegative = isNegative;
    }

    public double getFractionPart() {
        return fractionPart;
    }

    public double getIntPart() {
        return intPart;
    }

    public double getEPart() {
        return ePart;
    }

    public double getValue() {

        int coeficient = (isNegative) ? -1 : 1;
        double value = coeficient * (intPart + fractionPart);
        double mantiss = Math.pow(10, ePart);
        BigDecimal result = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(mantiss));
        return result.doubleValue();
    }


    /**
     * Example: "12.34" -> intPart = "12" -> 1*10^(1) + 2*10^(2)
     */
    private double countIntValue(String intString) {
        double sum = 0;
        int pow = intString.length() - 1;
        for (int i = 0; i < intString.length(); i++, pow--) {
            char currentChar = intString.charAt(i);
            int value = NumberUtils.parseDigitToInt(currentChar);
            sum += value * Math.pow(NumberUtils.NUMERIC_SYSTEM, pow);
        }
        return sum;

    }

    /**
     * Example: "12.34" -> fraction = "34" -> 3*10^(-1) + 4*10^(-2)
     */
    private double countFractionValue(String fractionString) {
        double sum = 0;
        int pow = -1;
        for (int i = 0; i < fractionString.length(); i++, pow--) {
            char currentChar = fractionString.charAt(i);
            int value = NumberUtils.parseDigitToInt(currentChar);
            sum += value * Math.pow(NumberUtils.NUMERIC_SYSTEM, pow);
        }
        return sum;
    }

}