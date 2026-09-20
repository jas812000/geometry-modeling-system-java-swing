/**
 * Represents a cylinder defined by its base radius and height.
 */
public class Cylinder extends ThreeDimensional {

    private final double radius;
    private final double height;

    /**
     * Creates a cylinder with the specified radius and height.
     *
     * @param radius radius of the cylinder's base
     * @param height height of the cylinder
     * @throws IllegalArgumentException if either dimension is not greater than zero
     */
    public Cylinder(double radius, double height) {
        validatePositiveFinite(radius, "Radius");
        validatePositiveFinite(height, "Height");

        this.radius = radius;
        this.height = height;
    }

    /**
     * Returns the radius of the cylinder's base.
     *
     * @return radius of the cylinder
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * Calculates the volume of the cylinder.
     *
     * @return volume of the cylinder
     */
    @Override
    public double getVolume() {
        return Math.PI * Math.pow(radius, 2) * height;
    }
}
