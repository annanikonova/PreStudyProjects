package com.nixsolutions.project1.task3;

import com.nixsolutions.project1.task2.Figure;
import com.nixsolutions.project1.task2.Triangle;

/**
 * Implementation of {@link Draweable} interface that is used for drawing {@link Triangle}.
 *
 * @author annnikon
 * @version 1.0
 */
public class TriangleDrawer implements Draweable {

    /**
     * Shows triangle points coordinates and its perimeter in standart output stream.
     *
     * @param figure triangle object that must be drawn
     * @throws IllegalArgumentException if figure`s type is not a Triangle
     */
    public void draw(Figure figure) {
        if (figure.getClass() != Triangle.class) {
            throw new IllegalArgumentException("Cannot draw this figure");
        }
        Triangle triangle = (Triangle) figure; //casting is possible
        System.out.println(String.format("Triangle with main point %s, " +
                        " point2 %s, " +
                        " point3 %s, " +
                        " and perimeter %4.2f",
                triangle.getMainPoint().toString(),
                triangle.getPoint2().toString(),
                triangle.getPoint3().toString(),
                triangle.getPerimeter()));


    }
}
