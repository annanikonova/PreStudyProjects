package com.nixsolutions.project3;

import interfaces.task3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by annnikon on 26.01.17.
 */
public class StringUtilsImpl implements StringUtils {
    public static final String EMPTY_STRING = "";
    /**
     * Separator can be used for dividing int and fractal parts of number.
     */
    public static final String DOT_SEPARATOR = ".";
    /**
     * Separator can be used for dividing int and fractal parts of number.
     */
    public static final String COMMA_SEPARATOR = ",";

    public static final String E_SEPARATOR = "e";
    public static final String SPACE_SEPARATOR=" ";

    @Override
    public String invert(String var1) {
        if (var1 == null || var1.length()==0) {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = var1.length() - 1; i >= 0; i--) {
            builder.append(var1.charAt(i));
        }
        return builder.toString();
    }

    @Override
    public String compareWords(String var1, String var2) {

        if (var1 == null || var2 == null) {
            throw new NullPointerException("Null parameter given for comparing.");
        }
        Set<Character> uniqueChars1 = getUniqueChars(var1);
        Set<Character> uniqueChars2 = getUniqueChars(var2);
        uniqueChars1.removeAll(uniqueChars2);
        StringBuilder result = new StringBuilder();
        for (char symbol : uniqueChars1) {
            result.append(symbol);
        }
        return result.toString();
    }

    /**
     * Returns set of unique characters,
     * in the order how they appear in string.
     */
    private Set<Character> getUniqueChars(String original) {
        Set<Character> uniqueChars = new LinkedHashSet<Character>();
        for (char symbol : original.toCharArray()) {
            uniqueChars.add(symbol);
        }
        return uniqueChars;
    }

    @Override
    public double parseDouble(String var1) {
        if (var1 == null) {
            throw new NullPointerException("Null string given.");
        }
        if (var1.length()==0) {
           throw new IllegalArgumentException("Empty string given.");
        }
        if(var1.startsWith(SPACE_SEPARATOR)) {
            InputException cause=new InputException(var1);
            throw new IllegalArgumentException("String must begin from a digit.",
                    cause);
        }

        /*Get only first number before space*/
        String analyzedString = var1.split(SPACE_SEPARATOR)[0];
        DoubleNumber number = new DoubleNumber();

        /*Detect minus*/
        if (analyzedString.startsWith("-")) {
            number.setNegative(true);
            analyzedString = analyzedString.substring(1);
        }

        /*Detect e separator*/
        if (analyzedString.contains(E_SEPARATOR)) {
            String[] parts = analyzedString.split(E_SEPARATOR);
            if(parts.length!=2
                    || analyzedString.endsWith(E_SEPARATOR))  {
                InputException cause=new InputException(analyzedString);
                throw new IllegalArgumentException("After e should be value of an expotential part.",
                        cause);
            }
            analyzedString = parts[0];
            number.setEPart(parts[1]);
        }

        /*Detect separator*/
        String currentSeparator = getSeparator(analyzedString);

        /*If separator is absent, we have an int number (fractionPart="0"). */
        if (currentSeparator == null) {
            number.setIntPart(analyzedString);
        }
        else {
           String[] array = analyzedString.split(Pattern.quote(currentSeparator));
            if (array.length != 2) {
                InputException cause=new InputException(analyzedString);
                throw new IllegalArgumentException("After separator should be minimum 1 digit.",
                        cause);
            }
            number.setIntPart(array[0]);
            number.setFractionPart(array[1]);

        }

        return number.getValue();
    }

    public String getSeparator(String analyzedString) {

        if (analyzedString.contains(DOT_SEPARATOR)) {
            return DOT_SEPARATOR;
        } else if (analyzedString.contains(COMMA_SEPARATOR)) {
            return COMMA_SEPARATOR;
        }
        return null;

    }

    public static void testInvert() {
        System.out.println("\nInvert test:");
        StringUtilsImpl impl = new StringUtilsImpl();
        String inverted = impl.invert("Direct");
        System.out.println("1: " + inverted); //tceriD
        System.out.println("2: " + impl.invert(inverted)); //Direct
        System.out.println("3: " + impl.invert(null)); // EMPTY_STRING
        if (impl.invert(null).equals(EMPTY_STRING)) //true
        {
            System.out.println("an empty string returned, ok");
        }

    }

    public static void testCompare() {
        System.out.println("\nCompare test:");
        StringUtilsImpl impl = new StringUtilsImpl();
        System.out.println("1: " + impl.compareWords("12aa4", "aaa")); //"124"
        String example2 = impl.compareWords("texttext", "texttexttext");
        System.out.println("2: " + example2); // EMPTY_STRING
        if (example2.equals(EMPTY_STRING)) //true
        {
            System.out.println("an empty string returned, ok");
        }
        System.out.println("3: " + impl.compareWords("stringSPECIAL", "String")); //"sPECIAL"
        try {
            System.out.print("4: ");
            System.out.println(impl.compareWords(null, "a")); //null parameter
        } catch (NullPointerException ex) {
            MyLogger.log(ex);
        }
        try {
            System.out.print("5: ");
            System.out.println(impl.compareWords("b", null)); //null parameter
        } catch (NullPointerException ex) {
            MyLogger.log(ex);
        }
    }

    public static void testParse() {

        System.out.println("\nParse test:");
        StringUtilsImpl impl = new StringUtilsImpl();
        System.out.println("1: " + impl.parseDouble("-1,5")); // = -1.5
        System.out.println("2: " + impl.parseDouble("0.5")); // = 0.5
        System.out.println("3: " + impl.parseDouble("0,5")); // = 0.5
        System.out.println("4: " + impl.parseDouble("0000.5")); // = 0.5
        System.out.println("5: " + (impl.parseDouble("0.5") ==
                impl.parseDouble("0000,5"))); //true
        System.out.println("6: " + impl.parseDouble(".5")); //allowed in Java, = 0.5
        System.out.println("7: " + impl.parseDouble("-.5")); // allowed in Java, = - 0.5
        System.out.println("8: " + impl.parseDouble("-5")); // = -5.0
        System.out.println("9: " + impl.parseDouble("-005")); // = -5.0
        System.out.println("10: " + impl.parseDouble("50.4 5")); // only the first number will be parsed
        System.out.println("11: " + impl.parseDouble(".4 .")); // only the first number will be parsed
        try {
            System.out.print("12: ");
            System.out.println(impl.parseDouble("50.")); //at least 1 digit after dot
        } catch (IllegalArgumentException e) {

            MyLogger.log(e);
        }
        try {
            System.out.print("13: ");
            System.out.println(impl.parseDouble("ab.4")); // non-numeric symbols in int part
        } catch (IllegalArgumentException e) {
            MyLogger.log(e);
        }
        try {
            System.out.print("13: ");
            System.out.println(impl.parseDouble("50.-4")); // non-numeric symbols in fr.part
        } catch (IllegalArgumentException e) {
            MyLogger.log(e);
        }
        try {
            System.out.print("14: ");
            System.out.println(impl.parseDouble(null)); // null string
        } catch (NullPointerException e) {
            MyLogger.log(e);
        }

        System.out.println("15: " + impl.parseDouble("1.2563e+1")); //= 12.563
        System.out.println(impl.parseDouble("1.2563e+1")==1.2563e+1); //true
        System.out.println("16: " + impl.parseDouble("1.2563e-1")); // = =0.12563
        try {
            System.out.print("17: ");
            System.out.println(impl.parseDouble("1.23e.11")); //after e should be +,- or 0
        } catch (IllegalArgumentException e) {
            MyLogger.log(e);
        }

    }

    public static void main(String[] args) {

        testInvert();
        testCompare();
        testParse();
        System.out.println("See full error stacktrace in log.txt");

    }

}
