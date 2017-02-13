package com.nixsolutions.project1.task2;

/**
 * Class <code>Circle</code> extends abstract class {@link Figure}.
 * Main point of a circle is its center.
 * Circle also has a radius.
 *
 * @author annnikon
 * @version 1.0
 */
public class Circle extends Figure {
    /**
     * Constant that defines a default radius for the circle.
     */
    public static final double DEFAULT_RADIUS = 1.0;

    /**
     * Constant that defines a default center point for the circle.
     */
    public static final Point DEFAULT_CENTER = new Point(0, 0);


    /**
     * Distance between center and any point of a circle.
     * Cannot be negative.
     */
    private double radius;

    /**
     * Constructor for circles.
     * Any circle can be defined by its center and radius.
     */
    public Circle(Point center, double radius) {
        super(center);
        this.radius = radius;

    }

    /**
     * Default constructor creates a new circle with default radius and center.
     */
    public Circle() {
        super(DEFAULT_CENTER);
        this.radius = DEFAULT_RADIUS;
    }

    /**
     * Getter for private field radius.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Setter for private field radius.
     * Verifies that param is bigger then zero.
     * Else sets default radius value.
     *
     * @param radius distance from center any to point on circle`s arc.
     */
    public void setRadius(double radius) {
        this.radius = (radius > 0) ? radius : DEFAULT_RADIUS;
    }


    /**
     * Counts length of the circle arc.
     * Uses a formulae:
     * <p>P  = 2  * {@link Math#PI} * {@link Circle#radius}^2</p>
     *
     * @return perimeter of the circle in pixels.
     */
    @Override
    public double getPerimeter() {
        return (2 * Math.PI * getRadius());
    }

    /**
     * Shows on console coordinates of main point of a circle.
     */
    @Override
    public void showCoordinates() {
        System.out.println("Circle with center: " + getMainPoint().toString());

    }


    /**
     * Changes the coordinates of circle center.
     * Radius and perimeter are not changed.
     *
     * @param xDirection Y-axis change in pixels.
     * @param yDirection Y-axis change in pixels.
     */
    @Override
    public void move(double xDirection, double yDirection) {
        double x = getMainPoint().getX();
        double y = getMainPoint().getY();
        setMainPoint((new Point(x + xDirection, y + yDirection)));
    }

    /**
     * Scales circle.
     * Changes circle radius in zoomSize times.
     * <p>If zoomSize is bigger than 1, the circle radius will be increased.</p>
     * <p>If zoomSize is between 0 and 1,the radius will be decreased.</p>
     * <p>For negative soom size, the radius will not be changed.</p>
     *
     * @param zoomSize coefficient of scaling, bigger than 0.
     * @throws IllegalArgumentException if parameter is not positive.
     */
    @Override
    public void scale(double zoomSize) {
        if (zoomSize <= 0) {
            throw new IllegalArgumentException("Zoom size should be positive");
        } else {
            setRadius(getRadius() * zoomSize);
        }
    }


}
