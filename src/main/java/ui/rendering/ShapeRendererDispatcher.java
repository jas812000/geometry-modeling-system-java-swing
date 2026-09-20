package ui.rendering;

import geometry.Shape;
import geometry.threedimensional.Cone;
import geometry.threedimensional.Cube;
import geometry.threedimensional.Cylinder;
import geometry.threedimensional.Sphere;
import geometry.threedimensional.Torus;
import geometry.twodimensional.Circle;
import geometry.twodimensional.Rectangle;
import geometry.twodimensional.Square;
import geometry.twodimensional.Triangle;
import ui.rendering.threedimensional.ConeRenderer;
import ui.rendering.threedimensional.CubeRenderer;
import ui.rendering.threedimensional.CylinderRenderer;
import ui.rendering.threedimensional.SphereRenderer;
import ui.rendering.threedimensional.TorusRenderer;
import ui.rendering.twodimensional.CircleRenderer;
import ui.rendering.twodimensional.RectangleRenderer;
import ui.rendering.twodimensional.SquareRenderer;
import ui.rendering.twodimensional.TriangleRenderer;

import java.awt.Graphics2D;

/**
 * Selects the renderer responsible for drawing a supported geometric shape.
 *
 * <p>This class keeps shape-type dispatch out of the Swing drawing panel so
 * the panel is responsible only for preparing the graphics context and
 * requesting that a shape be rendered.</p>
 */
public final class ShapeRendererDispatcher {

    private static final CircleRenderer CIRCLE_RENDERER =
            new CircleRenderer();

    private static final RectangleRenderer RECTANGLE_RENDERER =
            new RectangleRenderer();

    private static final SquareRenderer SQUARE_RENDERER =
            new SquareRenderer();

    private static final TriangleRenderer TRIANGLE_RENDERER =
            new TriangleRenderer();

    private static final SphereRenderer SPHERE_RENDERER =
            new SphereRenderer();

    private static final CubeRenderer CUBE_RENDERER =
            new CubeRenderer();

    private static final ConeRenderer CONE_RENDERER =
            new ConeRenderer();

    private static final CylinderRenderer CYLINDER_RENDERER =
            new CylinderRenderer();

    private static final TorusRenderer TORUS_RENDERER =
            new TorusRenderer();

    private ShapeRendererDispatcher() {
        // Prevent instantiation because renderer dispatch is shared.
    }

    /**
     * Draws a supported shape with its corresponding renderer.
     *
     * @param g2 graphics context used for rendering
     * @param shape shape to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     * @throws IllegalArgumentException if the shape type is unsupported
     */
    public static void draw(
            Graphics2D g2,
            Shape shape,
            int width,
            int height) {

        if (shape instanceof Circle circle) {
            CIRCLE_RENDERER.draw(g2, circle, width, height);
        } else if (shape instanceof Rectangle rectangle) {
            RECTANGLE_RENDERER.draw(g2, rectangle, width, height);
        } else if (shape instanceof Square square) {
            SQUARE_RENDERER.draw(g2, square, width, height);
        } else if (shape instanceof Triangle triangle) {
            TRIANGLE_RENDERER.draw(g2, triangle, width, height);
        } else if (shape instanceof Sphere sphere) {
            SPHERE_RENDERER.draw(g2, sphere, width, height);
        } else if (shape instanceof Cube cube) {
            CUBE_RENDERER.draw(g2, cube, width, height);
        } else if (shape instanceof Cone cone) {
            CONE_RENDERER.draw(g2, cone, width, height);
        } else if (shape instanceof Cylinder cylinder) {
            CYLINDER_RENDERER.draw(g2, cylinder, width, height);
        } else if (shape instanceof Torus torus) {
            TORUS_RENDERER.draw(g2, torus, width, height);
        } else {
            throw new IllegalArgumentException(
                    "Unsupported shape type: "
                            + (shape == null
                            ? "null"
                            : shape.getClass().getSimpleName()));
        }
    }

    /**
     * Determines whether the supplied shape has a registered renderer.
     *
     * @param shape shape to inspect
     * @return {@code true} when the shape can be rendered
     */
    public static boolean isSupported(Shape shape) {
        return shape instanceof Circle
                || shape instanceof Rectangle
                || shape instanceof Square
                || shape instanceof Triangle
                || shape instanceof Sphere
                || shape instanceof Cube
                || shape instanceof Cone
                || shape instanceof Cylinder
                || shape instanceof Torus;
    }
}
