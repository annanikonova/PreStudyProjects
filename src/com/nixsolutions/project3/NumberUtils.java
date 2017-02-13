package com.nixsolutions.project3;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with static methods
 * for parsing characters which represents digits.
 * Created by annnikon on 27.01.17.
 */
public class NumberUtils {
    /**
     * Numeric system used for counting.
     */
    public static final int NUMERIC_SYSTEM = 10;

    /**
     * In 10-symbol system allowed characters are:
     * {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
     */
    public static final List<Character> ALLOWED_DIGITS = generateAllowedDigits();

    /**
     * Index of character in list is its integer value.
     */
    public static List<Character> generateAllowedDigits() {
        ArrayList<Character> result = new ArrayList<>(NUMERIC_SYSTEM);
        for (int i = 0; i < NUMERIC_SYSTEM; i++) {
            result.add(Character.forDigit(i, NUMERIC_SYSTEM));
        }
        return result;
    }

    /**
     * Returns true if string consists only the symbols
     * from ALLOWED_DIGITS array.
     */
    public static boolean isStringValid(String s) {

        for (char element : s.toCharArray()) {

            if (!ALLOWED_DIGITS.contains(element)) {
                return false;
            }

        }
        return true;
    }

    /**
     * Returns integer value for every char
     * that represents digit in a number.
     */
    public static int parseDigitToInt(char element) {
        return ALLOWED_DIGITS.indexOf(element);
    }


}
