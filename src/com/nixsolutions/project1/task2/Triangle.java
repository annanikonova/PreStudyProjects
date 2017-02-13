package com.nixsolutions.project1.task2;

/**
 * Class <code>Triangle</code> extends abstract class {@link Figure}.
 * <p>Triangle is a figure described by 3 apexes on the flat.</p>
 * <p>Main point of a triangle is the apex we start drawing with.</p>
 * <p>Triangle also has point2 and point3.</p>
 *
 * @author annnikon
 * @version 1.0
 */
public class Triangle extends Figure {

    /**
     * Private field for the second apex of a triangle. {@see Point}
     */
    private Point point2;

    /**
     * Private field for the second apex of a triangle. {@see Point}
     */
    private Point point3;

    /**
     * Getter for the private field of the second apex.
     *
     * @return the second point of triangle.
     */
    public Point getPoint2() {
        return point2;
    }

    /**
     * Setter for the private field of the second apex.
     *
     * @param point2 the second apex of the triangle
     */
    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    /**
     * Getter for the private field of the third apex.
     *
     * @return the trird point of triangle.
     */
    public Point getPoint3() {
        return point3;
    }

    /**
     * Setter for the private field of the third apex.
     *
     * @param point3 the trird apex of the triangle
     */
    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    /**
     * Constructor for a triangle.
     * Any triangle can be defined by its 3 points (apexes).
     *
     * @param mainPoint point which binds the triangle
     * @param point2    the second apex of the triangle
     * @param point3    the trird apex of the triangle
     */
    public Triangle(Point mainPoint, Point point2, Point point3) {
        super(mainPoint);
        this.point2 = point2;
        this.point3 = point3;

    }

    /**
     * Counts summary length of tree sides, that connect apexes.
     * <p>Perimeter = side1+ side2 + side3</p>
     *
     * @return perimeter of the current triangle in pixels, positive value.
     * @see Point#distanceTo(Point)
     */
    @Override
    public double getPerimeter() {
        return getMainPoint().distanceTo(point2) +
                getMainPoint().distanceTo(point3) +
                point2.distanceTo(point3);

    }

    /**
     * Shows on console coordinates of main point of a triangle
     * and two other apexes.
     */
    @Override
    public void showCoordinates() {
        System.out.println(String.format("Triangle " +
                        "with main point: %s, point2 %s and point3 %s",
                getMainPoint().toString(),
                getPoint2().toString(),
                getPoint3().toString()));

    }

    /**
     * Changes coordinates by X and Y for every point of a triangle.
     * Perimeter is not changed.
     *
     * @param xDirection Y-axis change in pixels.
     * @param yDirection Y-axis change in pixels.
     */
    @Override
    public void move(double xDirection, double yDirection) {
        setMainPoint(new Point(getMainPoint().getX() + xDirection,
                getMainPoint().getY() + yDirection));
        setPoint2(new Point(getPoint2().getX() + xDirection,
                getPoint2().getY() + yDirection));
        setPoint3(new Point(getPoint3().getX() + xDirection,
                getPoint3().getY() + yDirection));

    }

    /**
     * Scales triangle relatively its main point in zoomSize times.
     * <p>Main point`s coordinates are not changed.</p>
     * <p> Perimeter and each side is changed in <b>zoomSize</b> times.</p>
     * To find new coordinates of point2 and point3, the formulae is used:
     * <p><i> x' = k(x-x0) + x0 </i></p>
     * Where x` - new coordinate on X,
     * <p>x - old coordinate on X,</p>
     * <p>x0 - coordinate of a main point.</p>
     * <p>The same formula eis for Y coordinate.</p>
     *
     * @param zoomSize shows in how many times sides length will be changed.
     * @throws IllegalArgumentException if the scale size is not positive.
     */

    @Override
    public void scale(double zoomSize) {
        if (zoomSize <= 0) {
            throw new IllegalArgumentException("Scale coeficient cannot be negative");
        } else {
            double newX2 = zoomSize *
                    (getPoint2().getX() - getMainPoint().getX()) +
                    getMainPoint().getX();
            double newY2 = zoomSize *
                    (getPoint2().getY() - getMainPoint().getY()) +
                    getMainPoint().getY();
            double newX3 = zoomSize *
                    (getPoint3().getX() - getMainPoint().getX()) +
                    getMainPoint().getX();
            double newY3 = zoomSize *
                    (getPoint3().getY() - getMainPoint().getY()) +
                    getMainPoint().getY();
            setPoint2(new Point(newX2, newY2));
            setPoint3(new Point(newX3, newY3));
        }

    }
}
