package geometry;

import geometry.threedimensional.ThreeDimensional;
import geometry.twodimensional.TwoDimensional;

/**
 * Base class for all geometric shapes in the application.
 *
 * <p>Each shape records whether it represents a two-dimensional or
 * three-dimensional geometric object. Concrete calculation behavior is
 * defined by the {@link TwoDimensional} and {@link ThreeDimensional}
 * subclasses.</p>
 */
public abstract class Shape {

    private final int numberOfDimensions;

    /**
     * Creates a shape with the specified number of dimensions.
     *
     * @param numberOfDimensions number of spatial dimensions represented
     */
    protected Shape(int numberOfDimensions) {
        this.numberOfDimensions = numberOfDimensions;
    }

    /**
     * Returns the number of dimensions represented by this shape.
     *
     * @return number of dimensions
     */
    public int getNumberOfDimensions() {
        return numberOfDimensions;
    }

    /**
     * Validates that a geometric measurement is finite and greater than zero.
     *
     * @param value measurement to validate
     * @param name name of the measurement used in the error message
     * @throws IllegalArgumentException if the value is not finite or is not
     *                                  greater than zero
     */
    protected static void validatePositiveFinite(
            double value,
            String name) {

        if (!Double.isFinite(value) || value <= 0) {
            throw new IllegalArgumentException(
                    "Please enter a "
                            + name.toLowerCase()
                            + " greater than zero.");
        }
    }
}
