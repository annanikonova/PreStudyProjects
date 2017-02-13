package com.nixsolutions.project1.task3;

import com.nixsolutions.project1.task2.Circle;
import com.nixsolutions.project1.task2.Figure;

/**
 * Implementation of {@link Draweable} interface for drawing {@link Circle}
 *
 * @author annnikon
 * @version 1.0
 */
public class CircleDrawer implements Draweable {

    /**
     * Represents information about circle: its center and perimeter.
     * To simplify, coordinates are formatted
     * as decimals with 2 characters after point.
     *
     * @param figure figure which type should equal the Circle type
     * @throws IllegalArgumentException if figure type is different from Circle.
     */

    public void draw(Figure figure) {
        if (figure.getClass() != Circle.class) {
            throw new IllegalArgumentException("Cannot draw this figure");
        }
        Circle circle = (Circle) figure; //class casting is possible
        System.out.println(String.format("Circle with center %s, " +
                        "radius %4.2f and perimeter %4.2f",
                circle.getMainPoint().toString(),
                circle.getRadius(),
                circle.getPerimeter()));
    }
}
