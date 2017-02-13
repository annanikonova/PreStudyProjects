package com.nixsolutions.project2;

/**
 * Demonstrates usage of implemented classes.
 */
public class Tester {
    public static void main(String[] args) {
        FractionNumberOperationImpl operation = new FractionNumberOperationImpl();
        FractionNumberImpl a = new FractionNumberImpl(5, -6);
        FractionNumberImpl b = new FractionNumberImpl(10, 8);
        FractionNumberImpl c = new FractionNumberImpl(-10, -8);
        FractionNumberImpl d = new FractionNumberImpl(0, -3);

        System.out.println("a: " + a.toStringValue() + " = " + a.value());
        System.out.println("b: " + b.toStringValue() + " = " + b.value());
        System.out.println("c: " + c.toStringValue() + " = " + c.value());
        System.out.println("d: " + d.toStringValue() + " = " + d.value());

        try {
            FractionNumberImpl e = new FractionNumberImpl(2, 0);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("a+b: " + operation.add(a, b).toStringValue());
        System.out.println("a-b: " + operation.sub(a, b).toStringValue());
        System.out.println("a*b: " + operation.mul(a, b).toStringValue());
        System.out.println("a/b: " + operation.div(a, b).toStringValue());

        System.out.println("a*d: " + operation.mul(a, d).toStringValue());
        try {
            System.out.println("a/d: " + operation.div(a, d).toStringValue());
        } catch (ArithmeticException ex) {
            System.out.println(ex.getMessage());
        }


    }
}
