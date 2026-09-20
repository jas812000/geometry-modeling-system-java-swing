/**
 * Represents a cone defined by its base radius and height.
 */
public class Cone extends ThreeDimensional {

    private final double radius;
    private final double height;

    /**
     * Creates a cone with the specified radius and height.
     *
     * @param radius radius of the cone's base
     * @param height height of the cone
     * @throws IllegalArgumentException if either dimension is not greater than zero
     */
    public Cone(double radius, double height) {
        validatePositiveFinite(radius, "Radius");
        validatePositiveFinite(height, "Height");

        this.radius = radius;
        this.height = height;
    }

    /**
     * Returns the radius of the cone's base.
     *
     * @return radius of the cone
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the height of the cone.
     *
     * @return height of the cone
     */
    public double getHeight() {
        return height;
    }

    /**
     * Calculates the volume of the cone.
     *
     * @return volume of the cone
     */
    @Override
    public double getVolume() {
        return (1.0 / 3.0) * Math.PI * Math.pow(radius, 2) * height;
    }
}
