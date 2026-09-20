package geometry.twodimensional;

import geometry.Shape;

/**
 * Abstract base class for all two-dimensional shapes.
 *
 * <p>Two-dimensional shapes have exactly two spatial dimensions and must
 * provide an implementation for calculating their area.</p>
 */
public abstract class TwoDimensional extends Shape {

    /**
     * Creates a two-dimensional shape.
     */
    protected TwoDimensional() {
        super(2);
    }

    /**
     * Calculates the area of the shape.
     *
     * @return area of the shape
     */
    public abstract double getArea();
}
