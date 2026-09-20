/**
 * Represents a cube defined by the length of its sides.
 */
public class Cube extends ThreeDimensional {

    private final double side;

    /**
     * Creates a cube with the specified side length.
     *
     * @param side length of each side
     * @throws IllegalArgumentException if the side length is not greater than zero
     */
    public Cube(double side) {
        validatePositiveFinite(side, "Side length");

        this.side = side;
    }

    /**
     * Returns the side length of the cube.
     *
     * @return side length of the cube
     */
    public double getSide() {
        return side;
    }

    /**
     * Calculates the volume of the cube.
     *
     * @return volume of the cube
     */
    @Override
    public double getVolume() {
        return Math.pow(side, 3);
    }
}
