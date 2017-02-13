package com.nixsolutions.project3;

import interfaces.task3.StringDiv;

/**
 * Created by annnikon on 26.01.17.
 */
public class StringDivImpl implements StringDiv {
    @Override
    public double div(String s, String s1) {
        StringUtilsImpl stringUtil = new StringUtilsImpl();
        double divident = stringUtil.parseDouble(s);
        double divisor = stringUtil.parseDouble(s1);
        if (divisor == 0) {
            InputException cause=new InputException(s1);
            throw new IllegalArgumentException("Division by zero");
        }
        return divident / divisor;
    }


    public static void testDiv() {
        StringDivImpl divUtil = new StringDivImpl();
        System.out.println("1: " + divUtil.div("10.5", "010,5")); // = 1.0
        try {
            System.out.print("2: ");
            System.out.println(divUtil.div("-1.5", "0")); //division by zero
        } catch (IllegalArgumentException ex) {
            MyLogger.log(ex);
        }
        try {
            System.out.print("3: ");
            System.out.println(divUtil.div("ddd", "2"));
        } //wrong input: non-numeric symbols
        catch (IllegalArgumentException ex) {
            MyLogger.log(ex);
        }
        System.out.println("4: " + divUtil.div(".8", "1.0")); // = 0.8
        try {
            System.out.print("5: ");
            System.out.println(divUtil.div(".8", "1.")); //wrong "1.": should be at least 1 digit
        } catch (IllegalArgumentException ex) {
            MyLogger.log(ex);
        }
        try {
            System.out.print("6: ");
            System.out.println(divUtil.div(null, "2"));
        } //null sting
        catch (NullPointerException ex) {
            MyLogger.log(ex);
        }

        try {
            System.out.print("7: ");
            System.out.println(divUtil.div("", "2"));
        } //null sting
        catch (IllegalArgumentException ex) {
            MyLogger.log(ex);
        }
    }

    public static void main(String[] args) {
        testDiv();
        System.out.println("See full error stacktrace in log.txt");
    }
}
