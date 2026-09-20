import geometry.threedimensional.Sphere;
import geometry.threedimensional.Cube;
import geometry.threedimensional.Cone;
import geometry.threedimensional.Cylinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies calculations, stored dimensions, and validation for the basic
 * three-dimensional shapes.
 */
class ThreeDimensionalShapesTest {

    private static final double DELTA = 1e-10;

    @Test
    void sphereStoresRadiusAndCalculatesVolume() {
        Sphere sphere = new Sphere(3);

        assertEquals(3, sphere.getRadius(), DELTA);
        assertEquals(36 * Math.PI, sphere.getVolume(), DELTA);
    }

    @Test
    void cubeStoresSideAndCalculatesVolume() {
        Cube cube = new Cube(3);

        assertEquals(3, cube.getSide(), DELTA);
        assertEquals(27, cube.getVolume(), DELTA);
    }

    @Test
    void coneStoresDimensionsAndCalculatesVolume() {
        Cone cone = new Cone(3, 4);

        assertEquals(3, cone.getRadius(), DELTA);
        assertEquals(4, cone.getHeight(), DELTA);
        assertEquals(12 * Math.PI, cone.getVolume(), DELTA);
    }

    @Test
    void cylinderStoresDimensionsAndCalculatesVolume() {
        Cylinder cylinder = new Cylinder(3, 4);

        assertEquals(3, cylinder.getRadius(), DELTA);
        assertEquals(4, cylinder.getHeight(), DELTA);
        assertEquals(36 * Math.PI, cylinder.getVolume(), DELTA);
    }

    @Test
    void sphereRejectsInvalidRadius() {
        assertInvalidSingleValue(value -> new Sphere(value));
    }

    @Test
    void cubeRejectsInvalidSide() {
        assertInvalidSingleValue(value -> new Cube(value));
    }

    @Test
    void coneRejectsInvalidRadius() {
        assertInvalidSingleValue(value -> new Cone(value, 2));
    }

    @Test
    void coneRejectsInvalidHeight() {
        assertInvalidSingleValue(value -> new Cone(2, value));
    }

    @Test
    void cylinderRejectsInvalidRadius() {
        assertInvalidSingleValue(value -> new Cylinder(value, 2));
    }

    @Test
    void cylinderRejectsInvalidHeight() {
        assertInvalidSingleValue(value -> new Cylinder(2, value));
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
