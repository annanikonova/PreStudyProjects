package com.nixsolutions.project1.task2;

/**
 * Abstract class for geometrical figures,
 * which can be showed on a screen, moved or scaled.
 * Every figure has a main point and a perimeter.
 *
 * @author annnikon
 * @version 1.0
 */
public abstract class Figure {

    /**
     * Main point is a point which binds the figure on the screen.
     * Ususally it is the point you start drawing the figure with.
     */
    private Point mainPoint;

    /**
     * Getter for private field mainPoint {@link Figure#mainPoint}
     */
    public Point getMainPoint() {
        return mainPoint;
    }

    /**
     * Setter for private field mainPoint {@link Figure#mainPoint}
     */
    public void setMainPoint(Point point) {
        this.mainPoint = point;
    }

    /**
     * Class constructor
     */
    public Figure(Point point) {
        this.mainPoint = point;
    }

    /**
     * Counts summary length of all the sides of figure.
     *
     * @return perimeter of the figure in pixels
     * Should be implemented by inheritors.
     */
    public abstract double getPerimeter();

    /**
     * Shows coordinates of a figure on the screen.
     * Should be implemented by inheritors.
     */
    public abstract void showCoordinates();

    /**
     * Changes coordinates of all figure points.
     *
     * @param xDirection Y-axis change in pixels relatively the main point
     * @param yDirection Y-axis change in pixels relatively the main point
     *                   Should be implemented by inheritants.
     */
    public abstract void move(double xDirection, double yDirection);


    /**
     * Scales figure  of all figure points.
     * Figure sides will be prorortionally changed.
     * Points will change their distance from the main point.
     *
     * @param zoomSize coefficient of scaling
     *                 Should be implemented by inheritants.
     */
    public abstract void scale(double zoomSize);
}