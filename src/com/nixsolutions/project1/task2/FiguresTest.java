package com.nixsolutions.project1.task2;

import com.nixsolutions.project1.task3.RandomFigureFactory;

/**
 * Class <code>FiguresTest</code> creates examples of
 * circle and triangle and manipulates them
 * to demonstrate appearance, inherited from {@link Figure}.
 */

public class FiguresTest {
    public static final double X_DIRECTION = 1;
    public static final double Y_DIRECTION = 1;
    public static final double SCALE_SIZE = 3;

    /**
     * Default constructor without parameters.
     *
     * @deprecated not used because class has no fields.
     */
    public FiguresTest() {

    }

    /**
     * Demonstrates how figures can show their coordinates, move and scale.
     * Uses constant values for direction and scale size.
     *
     * @param figure figure that should be tested
     */
    public static void testBehaviour(Figure figure) {
        System.out.println("Created a figure: ");
        figure.showCoordinates();
        System.out.println("Perimeter: " + figure.getPerimeter());
        System.out.println("Moved for: " +
                new Point(X_DIRECTION, Y_DIRECTION).toString());
        figure.move(X_DIRECTION, Y_DIRECTION);
        figure.showCoordinates();
        System.out.println("Scaled for: " + SCALE_SIZE);
        figure.scale(SCALE_SIZE);
        figure.showCoordinates();
        System.out.println("Perimeter: " + figure.getPerimeter());

    }


    /**
     * Main entrance point.
     * Creates circle and triangle from  randomly generated points,
     * and than executes test method for them.
     */
    public static void main(String[] args) {
        Circle circle = new Circle(RandomFigureFactory.getRandomPoint(),
                RandomFigureFactory.getRandomRadius());
        Triangle triangle = new Triangle(RandomFigureFactory.getRandomPoint(),
                RandomFigureFactory.getRandomPoint(),
                RandomFigureFactory.getRandomPoint());
        testBehaviour(circle);
        testBehaviour(triangle);

    }


}
