package com.nixsolutions.project2;

/**
 * Created by annnikon on 25.01.17.
 */
public class Utils {

    public static int greatestCommonDivisor(int a, int b) {
        return b == 0 ? a : greatestCommonDivisor(b, a % b);
    }


    public static int minimalCommonDividend(int a, int b) {
        return a / greatestCommonDivisor(a, b) * b;
    }
}
