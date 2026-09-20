import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies both triangle construction modes, area calculations,
 * classification, and validation.
 */
class TriangleTest {

    private static final double DELTA = 1e-10;

    @Test
    void baseAndHeightTriangleStoresDimensionsAndCalculatesArea() {
        Triangle triangle = new Triangle(6, 4);

        assertFalse(triangle.isDefinedByThreeSides());
        assertEquals(6, triangle.getBase(), DELTA);
        assertEquals(4, triangle.getHeight(), DELTA);
        assertEquals(0, triangle.getSideA(), DELTA);
        assertEquals(0, triangle.getSideB(), DELTA);
        assertEquals(0, triangle.getSideC(), DELTA);
        assertEquals(12, triangle.getArea(), DELTA);
    }

    @Test
    void threeSideTriangleStoresSidesAndUsesHeronsFormula() {
        Triangle triangle = new Triangle(3, 4, 5);

        assertTrue(triangle.isDefinedByThreeSides());
        assertEquals(0, triangle.getBase(), DELTA);
        assertEquals(0, triangle.getHeight(), DELTA);
        assertEquals(3, triangle.getSideA(), DELTA);
        assertEquals(4, triangle.getSideB(), DELTA);
        assertEquals(5, triangle.getSideC(), DELTA);
        assertEquals(6, triangle.getArea(), DELTA);
    }

    @Test
    void classifiesEquilateralTriangle() {
        assertEquals(
                "Equilateral Triangle",
                new Triangle(5, 5, 5).determineTriangleType());
    }

    @Test
    void classifiesRightScaleneTriangleRegardlessOfSideOrder() {
        assertEquals(
                "Right Scalene Triangle",
                new Triangle(3, 4, 5).determineTriangleType());

        assertEquals(
                "Right Scalene Triangle",
                new Triangle(5, 3, 4).determineTriangleType());
    }

    @Test
    void classifiesRightIsoscelesTriangle() {
        assertEquals(
                "Right Isosceles Triangle",
                new Triangle(1, 1, Math.sqrt(2)).determineTriangleType());
    }

    @Test
    void classifiesAcuteScaleneTriangle() {
        assertEquals(
                "Acute Scalene Triangle",
                new Triangle(4, 5, 6).determineTriangleType());
    }

    @Test
    void classifiesAcuteIsoscelesTriangle() {
        assertEquals(
                "Acute Isosceles Triangle",
                new Triangle(5, 5, 6).determineTriangleType());
    }

    @Test
    void classifiesObtuseScaleneTriangle() {
        assertEquals(
                "Obtuse Scalene Triangle",
                new Triangle(2, 3, 4).determineTriangleType());
    }

    @Test
    void classifiesObtuseIsoscelesTriangle() {
        assertEquals(
                "Obtuse Isosceles Triangle",
                new Triangle(2, 2, 3).determineTriangleType());
    }

    @Test
    void baseAndHeightTriangleCannotBeClassified() {
        Triangle triangle = new Triangle(6, 4);

        assertThrows(
                IllegalStateException.class,
                triangle::determineTriangleType);
    }

    @Test
    void baseAndHeightConstructorRejectsInvalidBase() {
        assertInvalidBaseOrHeight(value -> new Triangle(value, 2));
    }

    @Test
    void baseAndHeightConstructorRejectsInvalidHeight() {
        assertInvalidBaseOrHeight(value -> new Triangle(2, value));
    }

    @Test
    void threeSideConstructorRejectsInvalidIndividualSides() {
        assertInvalidBaseOrHeight(value -> new Triangle(value, 3, 4));
        assertInvalidBaseOrHeight(value -> new Triangle(3, value, 4));
        assertInvalidBaseOrHeight(value -> new Triangle(3, 4, value));
    }

    @Test
    void threeSideConstructorRejectsTriangleInequalityViolations() {
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(1, 2, 3));
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(1, 3, 2));
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(3, 1, 2));
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(1, 2, 4));
    }

    private void assertInvalidBaseOrHeight(ShapeFactory factory) {
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
