package com.nixsolutions.project1.task3;

import com.nixsolutions.project1.task2.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Factory class that has utils for:
 * <p>creating random figures, </p>
 * <p> adding them to list, </p>
 * <p> deciding, which of drawers will be used for
 * appearing figures on a screen. </p>
 * New drawers for new types of figures should be added into if
 * statements.
 *
 * @author annnikon
 * @version 1.0
 */
public class RandomFigureFactory {
    /**
     * Constant that determines number of figures in array for task2
     */
    public static final int INITIAL_CAPACITY = 10;

    /**
     * Constant that determines minimal radius for random circles
     */
    public static final double MIN_RADIUS = 0.1;
    /**
     * Constant that determines maximal radius for random circles
     */
    public static final double MAX_RADIUS = 10;

    /**
     * Default constructor without parameters.
     *
     * @deprecated not used because this class is just a set of static utils
     */
    public RandomFigureFactory() {

    }

    /**
     * Creates a point with random coordinates.
     * Coordinates may be positive or negative double values.
     *
     * @return {@link Point} with random generated x and y
     */
    public static Point getRandomPoint() {
        Random random = new Random();
        return new Point(random.nextDouble(), random.nextDouble());
    }

    /**
     * Generates random value for circle radius, which is bigger than
     * {@link RandomFigureFactory#MIN_RADIUS}
     * and less than
     * {@link RandomFigureFactory#MAX_RADIUS}
     *
     * @return value of radius for a circle
     */
    public static double getRandomRadius() {
        Random random = new Random();
        return MIN_RADIUS + (MAX_RADIUS - MIN_RADIUS) * random.nextDouble();
    }

    /**
     * Generates keyword for type of the figure, which will be created.
     *
     * @return "Circle" for {@link Circle} and "Triangle" for {@link Triangle}
     */

    public static String getRandomType() {
        Random random = new Random();
        if (random.nextInt() % 2 == 0) {
            return "Circle";
        } else {
            return "Triangle";
        }
    }

    /**
     * Finds concrete drawer for a given figure class.
     * If the class has not its own drawer, method will return a default one.
     *
     * @param figureClass class of figure that should be drawn on a screen.
     */
    public static Draweable getDrawer(Class figureClass) {

        if (figureClass.equals(Triangle.class)) {
            return new TriangleDrawer();

        } else if (figureClass.equals(Circle.class)) {
            return new CircleDrawer();

        } else {
            return new DefaultDrawer();
        }

    }

    /**
     * Demonstrates how different figures can be created, enplaced into container and then drawn.
     * Uses the keyword to identify the figure type.
     */
    public static void main(String[] args) {
        ArrayList<Figure> figures = new ArrayList<Figure>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            switch (getRandomType()) {
                case "Circle": {
                    Circle circle = new Circle(getRandomPoint(), getRandomRadius());
                    figures.add(circle);
                    break;
                }
                case "Triangle": {
                    Triangle triangle = new Triangle(getRandomPoint(),
                            getRandomPoint(),
                            getRandomPoint());
                    figures.add(triangle);
                    break;
                }

            }
        }
        for (int i = 0; i < figures.size(); i++) {
            System.out.print(i + " - ");
            Figure currentFigure = figures.get(i);
            Draweable drawer = getDrawer(currentFigure.getClass());
            drawer.draw(currentFigure);
        }


    }
}
