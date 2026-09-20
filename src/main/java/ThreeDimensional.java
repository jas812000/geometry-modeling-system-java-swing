/**
 * Abstract base class for all three-dimensional shapes.
 *
 * <p>Three-dimensional shapes have exactly three spatial dimensions and must
 * provide an implementation for calculating their volume.</p>
 */
public abstract class ThreeDimensional extends Shape {

    /**
     * Creates a three-dimensional shape.
     */
    protected ThreeDimensional() {
        super(3);
    }

    /**
     * Calculates the volume of the shape.
     *
     * @return volume of the shape
     */
    public abstract double getVolume();
}
