import geometry.threedimensional.Torus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies torus dimensions, volume calculation, and radius constraints.
 */
class TorusTest {

    private static final double DELTA = 1e-10;

    @Test
    void storesRadiiAndCalculatesVolume() {
        Torus torus = new Torus(4, 2);

        assertEquals(4, torus.getMajorRadius(), DELTA);
        assertEquals(2, torus.getMinorRadius(), DELTA);
        assertEquals(
                32 * Math.pow(Math.PI, 2),
                torus.getVolume(),
                DELTA);
    }

    @Test
    void rejectsInvalidMajorRadius() {
        assertInvalidValue(value -> new Torus(value, 1));
    }

    @Test
    void rejectsInvalidMinorRadius() {
        assertInvalidValue(value -> new Torus(2, value));
    }

    @Test
    void rejectsEqualRadii() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Torus(2, 2));
    }

    @Test
    void rejectsMajorRadiusSmallerThanMinorRadius() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Torus(1, 2));
    }

    private void assertInvalidValue(ShapeFactory factory) {
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
