package geometry.twodimensional;

/**
 * Represents a square defined by the length of its sides.
 */
public class Square extends TwoDimensional {

    private final double side;

    /**
     * Creates a square with the specified side length.
     *
     * @param side length of each side
     * @throws IllegalArgumentException if the side length is not greater than zero
     */
    public Square(double side) {
        validatePositiveFinite(side, "Side length");

        this.side = side;
    }

    /**
     * Returns the side length of the square.
     *
     * @return side length of the square
     */
    public double getSide() {
        return side;
    }

    /**
     * Calculates the area of the square.
     *
     * @return area of the square
     */
    @Override
    public double getArea() {
        return Math.pow(side, 2);
    }
}
