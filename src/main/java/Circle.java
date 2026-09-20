/**
 * Represents a circle defined by its radius.
 */
public class Circle extends TwoDimensional {

    private final double radius;

    /**
     * Creates a circle with the specified radius.
     *
     * @param radius radius of the circle
     * @throws IllegalArgumentException if the radius is not greater than zero
     */
    public Circle(double radius) {
        validatePositiveFinite(radius, "Radius");

        this.radius = radius;
    }

    /**
     * Returns the radius of the circle.
     *
     * @return radius of the circle
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Calculates the area of the circle.
     *
     * @return area of the circle
     */
    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}
