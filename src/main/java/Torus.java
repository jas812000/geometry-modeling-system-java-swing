/**
 * Represents a torus defined by its major and minor radii.
 *
 * <p>The major radius measures the distance from the center of the torus
 * to the center of its tube. The minor radius measures the radius of the
 * tube itself.</p>
 */
public class Torus extends ThreeDimensional {

    private final double majorRadius;
    private final double minorRadius;

    /**
     * Creates a torus with the specified major and minor radii.
     *
     * @param majorRadius distance from the center of the torus to the center
     *                    of its tube
     * @param minorRadius radius of the torus tube
     * @throws IllegalArgumentException if either radius is not greater than zero
     *                                  or the major radius is not greater than
     *                                  the minor radius
     */
    public Torus(double majorRadius, double minorRadius) {
        validatePositiveFinite(majorRadius, "Major radius");
        validatePositiveFinite(minorRadius, "Minor radius");

        if (majorRadius <= minorRadius) {
            throw new IllegalArgumentException(
                    "Major radius must be greater than minor radius.");
        }

        this.majorRadius = majorRadius;
        this.minorRadius = minorRadius;
    }

    /**
     * Returns the major radius of the torus.
     *
     * @return major radius
     */
    public double getMajorRadius() {
        return majorRadius;
    }

    /**
     * Returns the minor radius of the torus.
     *
     * @return minor radius
     */
    public double getMinorRadius() {
        return minorRadius;
    }

    /**
     * Calculates the volume of the torus.
     *
     * @return volume of the torus
     */
    @Override
    public double getVolume() {
        return 2 * Math.pow(Math.PI, 2)
                * majorRadius * Math.pow(minorRadius, 2);
    }
}
