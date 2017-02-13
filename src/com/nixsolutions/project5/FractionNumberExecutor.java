package com.nixsolutions.project5;

import com.nixsolutions.project2.FractionNumberImpl;
import com.nixsolutions.project2.FractionNumberOperationImpl;
import interfaces.task2.FractionNumber;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by annnikon on 01.02.17.
 */
public class FractionNumberExecutor {

    public static void printValues(ArrayList<FractionNumberImpl> list,
                                   String mode) {
        System.out.print("{");
        for (int i = 0; i < list.size(); i++) {
            switch (mode) {
                case "String":
                    System.out.print(list.get(i).toStringValue());
                    break;
                case "Double":
                    System.out.print(list.get(i).value());
                    break;
            }

            if (i != list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");

    }


    public static void main(String[] args) {
        ArrayList<FractionNumberImpl> list = new ArrayList<>();
        FractionNumberOperationImpl operation = new FractionNumberOperationImpl();
        FractionNumberImpl fraction1 = new FractionNumberImpl(1, 2);
        FractionNumberImpl fraction2 = new FractionNumberImpl(2, 5);
        FractionNumberImpl fraction3 = new FractionNumberImpl(1, 10);
        list.add(fraction1);
        list.add(fraction2);
        list.add(fraction3);
        Iterator iterator = list.iterator();
        FractionNumber sum = new FractionNumberImpl(0, 1);
        while (iterator.hasNext()) {
            sum = operation.add(sum, (FractionNumberImpl) iterator.next());
        }
        System.out.println("List:");
        printValues(list, "String");
        System.out.println("double values of this list:");
        printValues(list, "Double");
        System.out.println("Its sum: " + sum.toStringValue());
        System.out.println("Double value of sum: " + sum.value());


    }
}
