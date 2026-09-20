import geometry.twodimensional.Circle;
import geometry.twodimensional.Rectangle;
import geometry.twodimensional.Square;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies calculations, stored dimensions, and validation for the basic
 * two-dimensional shapes.
 */
class TwoDimensionalShapesTest {

    private static final double DELTA = 1e-10;

    @Test
    void circleStoresRadiusAndCalculatesArea() {
        Circle circle = new Circle(3);

        assertEquals(3, circle.getRadius(), DELTA);
        assertEquals(9 * Math.PI, circle.getArea(), DELTA);
    }

    @Test
    void rectangleStoresDimensionsAndCalculatesArea() {
        Rectangle rectangle = new Rectangle(4, 5);

        assertEquals(4, rectangle.getLength(), DELTA);
        assertEquals(5, rectangle.getWidth(), DELTA);
        assertEquals(20, rectangle.getArea(), DELTA);
    }

    @Test
    void squareStoresSideAndCalculatesArea() {
        Square square = new Square(4);

        assertEquals(4, square.getSide(), DELTA);
        assertEquals(16, square.getArea(), DELTA);
    }

    @Test
    void circleRejectsInvalidRadius() {
        assertInvalidSingleValue(value -> new Circle(value));
    }

    @Test
    void rectangleRejectsInvalidLength() {
        assertInvalidSingleValue(value -> new Rectangle(value, 2));
    }

    @Test
    void rectangleRejectsInvalidWidth() {
        assertInvalidSingleValue(value -> new Rectangle(2, value));
    }

    @Test
    void squareRejectsInvalidSide() {
        assertInvalidSingleValue(value -> new Square(value));
    }

    private void assertInvalidSingleValue(ShapeFactory factory) {
        assertThrows(IllegalArgumentException.class, () -> factory.create(0));
        assertThrows(IllegalArgumentException.class, () -> factory.create(-1));
        assertThrows(IllegalArgumentException.class, () -> factory.create(Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> factory.create(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class,
                () -> factory.create(Double.NEGATIVE_INFINITY));
    }

    @FunctionalInterface
    private interface ShapeFactory {
        void create(double value);
    }
}
