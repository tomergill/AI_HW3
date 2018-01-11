import java.util.Objects;

/**
 * Immutable Point class, has x and y values.
 */
public class Point {

    /**
     * x and y values.
     */
    private double x, y;

    /**
     * Constructor.
     *
     * @param x x-value of point.
     * @param y y-value of point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x value of the point.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y value of the point.
     */
    public double getY() {
        return y;
    }

    /**
     * Checks whether the object are equal or not, by x and y values.
     *
     * @param o Oject to check if equal to this.
     * @return true if equals, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    /**
     * @return Automatic Intellij hashcode.
     */
    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    /**
     * @return String representation of the point, "point={x=this.x, y=this.y}"
     */
    @Override
    public String toString() {
//        return "Point{" +
//                "x=" + x +
//                ", y=" + y +
//                '}';
        return "(" + x + ", " + y + ")";
    }
}
