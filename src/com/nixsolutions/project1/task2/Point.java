package com.nixsolutions.project1.task2;

/**
 * Class <code>Point</code> describes points in two-dimentional area
 * with <b>X</b> and <b>Y</b> coordinates.
 *
 * @author annnikon
 * @version 1.0
 */
public class Point {
    /**
     * Constant that defines a default abscissa value for a point.
     */
    public static final double DEFAULT_X = 0;
    /**
     * Constant that defines a default ordinate value for a point.
     */
    public static final double DEFAULT_Y = 0;

    /**
     * Abscissa of the point. Can be both positive and negative.
     */
    private double x;
    /**
     * Ordinate of the point. Can be both positive and negative.
     */
    private double y;

    /**
     * Constructor creates a new point by its x and y coordinates.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor creates a new point in the start of the coordinate system.
     */
    public Point() {
        this.x = DEFAULT_X;
        this.y = DEFAULT_Y;
    }

    /**
     * Getter for private field {@link Point#x}
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for private field {@link Point#y}
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for private field {@link Point#x}
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter for private field {@link Point#y}
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Counts a distance in pixels between 2 points.
     * <p> Uses a formulae for two points: </p>
     * <p><i> Distance = sqrt ((x1-x2)^2 + (y1-y2)^2) </i></p>
     *
     * @param anotherPoint a point with coordinates (x2; y2)
     * @return distance between current point and another point
     */
    public double distanceTo(Point anotherPoint) {
        return Math.sqrt(Math.pow(getX() - anotherPoint.getX(), 2) +
                Math.pow(getY() - anotherPoint.getY(), 2));
    }

    /**
     * Shows coordinates of a point as decimals
     * with 2 numbers after a comma
     * For example: (0.33; -0.00)
     */
    @Override
    public String toString() {
        return String.format("(%4.2f; %4.2f)", getX(), getY());
    }
}
