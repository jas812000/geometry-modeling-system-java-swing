package geometry.twodimensional;

/**
 * Represents a rectangle defined by its length and width.
 */
public class Rectangle extends TwoDimensional {

    private final double length;
    private final double width;

    /**
     * Creates a rectangle with the specified dimensions.
     *
     * @param length length of the rectangle
     * @param width width of the rectangle
     * @throws IllegalArgumentException if either dimension is not greater than zero
     */
    public Rectangle(double length, double width) {
        validatePositiveFinite(length, "Length");
        validatePositiveFinite(width, "Width");

        this.length = length;
        this.width = width;
    }

    /**
     * Returns the length of the rectangle.
     *
     * @return length of the rectangle
     */
    public double getLength() {
        return length;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return width of the rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Calculates the area of the rectangle.
     *
     * @return area of the rectangle
     */
    @Override
    public double getArea() {
        return length * width;
    }
}
