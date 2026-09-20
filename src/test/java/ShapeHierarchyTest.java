import geometry.twodimensional.Circle;
import geometry.twodimensional.Rectangle;
import geometry.twodimensional.Square;
import geometry.twodimensional.Triangle;
import geometry.threedimensional.Sphere;
import geometry.threedimensional.Cube;
import geometry.threedimensional.Cone;
import geometry.threedimensional.Cylinder;
import geometry.threedimensional.Torus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies dimensional metadata inherited through the shape hierarchy.
 */
class ShapeHierarchyTest {

    @Test
    void twoDimensionalShapesReportTwoDimensions() {
        assertEquals(2, new Circle(1).getNumberOfDimensions());
        assertEquals(2, new Rectangle(1, 2).getNumberOfDimensions());
        assertEquals(2, new Square(1).getNumberOfDimensions());
        assertEquals(2, new Triangle(3, 4).getNumberOfDimensions());
    }

    @Test
    void threeDimensionalShapesReportThreeDimensions() {
        assertEquals(3, new Sphere(1).getNumberOfDimensions());
        assertEquals(3, new Cube(1).getNumberOfDimensions());
        assertEquals(3, new Cone(1, 2).getNumberOfDimensions());
        assertEquals(3, new Cylinder(1, 2).getNumberOfDimensions());
        assertEquals(3, new Torus(2, 1).getNumberOfDimensions());
    }
}
