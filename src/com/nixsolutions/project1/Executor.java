package com.nixsolutions.project1;

import com.nixsolutions.project1.task1.Matrix;
import com.nixsolutions.project1.task2.FiguresTest;
import com.nixsolutions.project1.task3.RandomFigureFactory;

/**
 * The <code>Example</code> class contains application entrance point.
 * Start testing project features by running main() method.
 *
 * @author annnikon
 * @version 1.0
 */
public class Executor {

    /**
     * Main entrance point for the application.
     * Tests all tasks of the first project.
     */
    public static void main(String[] args) {
        System.out.println("\nTask1:");
        Matrix.main(args);
        System.out.println("\nTask2:");
        FiguresTest.main(args);
        System.out.println("\nTask3:");
        RandomFigureFactory.main(args);

    }
}
