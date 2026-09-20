/**
 * Represents a sphere defined by its radius.
 */
public class Sphere extends ThreeDimensional {

    private final double radius;

    /**
     * Creates a sphere with the specified radius.
     *
     * @param radius radius of the sphere
     * @throws IllegalArgumentException if the radius is not greater than zero
     */
    public Sphere(double radius) {
        validatePositiveFinite(radius, "Radius");

        this.radius = radius;
    }

    /**
     * Returns the radius of the sphere.
     *
     * @return radius of the sphere
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Calculates the volume of the sphere.
     *
     * @return volume of the sphere
     */
    @Override
    public double getVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }
}
