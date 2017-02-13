package com.nixsolutions.project1.task3;

import com.nixsolutions.project1.task2.Figure;

/**
 * Interface for classes that are able to show
 * figures on the screen.
 *
 * @author annnikon
 * @version 1.0
 */
public interface Draweable {
    /**
     * Implementation of this method should show figure`s coordinates
     * and perimeter on a console.
     *
     * @param figure instance of abstract class {@link Figure}
     */
    void draw(Figure figure);

}
