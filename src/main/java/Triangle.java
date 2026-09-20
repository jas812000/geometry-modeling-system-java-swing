/**
 * Represents a triangle defined either by a base and height or by three
 * side lengths.
 *
 * <p>A triangle created from three sides can also be classified according
 * to its side lengths and angles.</p>
 */
public class Triangle extends TwoDimensional {

    private final double base;
    private final double height;
    private final double sideA;
    private final double sideB;
    private final double sideC;
    private final boolean definedByThreeSides;

    /**
     * Creates a triangle using a base and height.
     *
     * @param base base of the triangle
     * @param height height of the triangle
     * @throws IllegalArgumentException if either dimension is not greater than zero
     */
    public Triangle(double base, double height) {
        validatePositiveFinite(base, "Base");
        validatePositiveFinite(height, "Height");

        this.base = base;
        this.height = height;
        this.sideA = 0;
        this.sideB = 0;
        this.sideC = 0;
        this.definedByThreeSides = false;
    }

    /**
     * Creates a triangle using three side lengths.
     *
     * @param sideA length of side A
     * @param sideB length of side B
     * @param sideC length of side C
     * @throws IllegalArgumentException if a side is not greater than zero or
     *                                  the sides cannot form a valid triangle
     */
    public Triangle(double sideA, double sideB, double sideC) {
        validatePositiveFinite(sideA, "Side A");
        validatePositiveFinite(sideB, "Side B");
        validatePositiveFinite(sideC, "Side C");

        if (sideA + sideB <= sideC
                || sideA + sideC <= sideB
                || sideB + sideC <= sideA) {
            throw new IllegalArgumentException(
                    "The side lengths do not form a valid triangle.");
        }

        this.base = 0;
        this.height = 0;
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.definedByThreeSides = true;
    }

    /**
     * Returns the base when the triangle was created from a base and height.
     *
     * @return base of the triangle, or zero when defined by three sides
     */
    public double getBase() {
        return base;
    }

    /**
     * Returns the height when the triangle was created from a base and height.
     *
     * @return height of the triangle, or zero when defined by three sides
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the length of side A.
     *
     * @return side A, or zero when defined by a base and height
     */
    public double getSideA() {
        return sideA;
    }

    /**
     * Returns the length of side B.
     *
     * @return side B, or zero when defined by a base and height
     */
    public double getSideB() {
        return sideB;
    }

    /**
     * Returns the length of side C.
     *
     * @return side C, or zero when defined by a base and height
     */
    public double getSideC() {
        return sideC;
    }

    /**
     * Returns whether this triangle was defined using three side lengths.
     *
     * @return {@code true} when defined by three sides; otherwise {@code false}
     */
    public boolean isDefinedByThreeSides() {
        return definedByThreeSides;
    }

    /**
     * Calculates the area of the triangle.
     *
     * <p>Base-and-height triangles use the standard one-half base times
     * height formula. Three-sided triangles use Heron's formula.</p>
     *
     * @return area of the triangle
     */
    @Override
    public double getArea() {
        if (!definedByThreeSides) {
            return (base * height) / 2.0;
        }

        double semiPerimeter = (sideA + sideB + sideC) / 2.0;

        return Math.sqrt(
                semiPerimeter
                        * (semiPerimeter - sideA)
                        * (semiPerimeter - sideB)
                        * (semiPerimeter - sideC));
    }

    /**
     * Classifies a triangle defined by three side lengths.
     *
     * @return triangle classification
     * @throws IllegalStateException if the triangle was defined only by a
     *                               base and height
     */
    public String determineTriangleType() {
        if (!definedByThreeSides) {
            throw new IllegalStateException(
                    "Triangle type cannot be determined from base and height alone.");
        }

        boolean equilateral =
                Double.compare(sideA, sideB) == 0
                        && Double.compare(sideB, sideC) == 0;

        boolean isosceles =
                Double.compare(sideA, sideB) == 0
                        || Double.compare(sideA, sideC) == 0
                        || Double.compare(sideB, sideC) == 0;

        if (equilateral) {
            return "Equilateral Triangle";
        }

        double[] sides = {sideA, sideB, sideC};
        java.util.Arrays.sort(sides);

        double aSquared = Math.pow(sides[0], 2);
        double bSquared = Math.pow(sides[1], 2);
        double cSquared = Math.pow(sides[2], 2);

        double comparison = aSquared + bSquared;
        double tolerance = 1e-10;

        String angleType;

        if (Math.abs(comparison - cSquared) < tolerance) {
            angleType = "Right";
        } else if (comparison > cSquared) {
            angleType = "Acute";
        } else {
            angleType = "Obtuse";
        }

        return angleType + (isosceles
                ? " Isosceles Triangle"
                : " Scalene Triangle");
    }
}
