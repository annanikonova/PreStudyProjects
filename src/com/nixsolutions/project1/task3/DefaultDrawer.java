package com.nixsolutions.project1.task3;

import com.nixsolutions.project1.task2.Figure;

/**
 * Implementation of {@link Draweable} interface for drawing {@link Figure}
 * which type is unknown.
 *
 * @author annnikon
 * @version 1.0
 */
public class DefaultDrawer implements Draweable {
    /**
     * Shows that user tried to draw a figure which type is not specified
     * in concrete drawer.
     *
     * @param figure figure which type has not its own drawer
     */
    @Override
    public void draw(Figure figure) {
        System.out.println("I am not able to draw unknown figure");
    }
}
