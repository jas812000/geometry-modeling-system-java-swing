package ui.rendering.threedimensional;

import geometry.threedimensional.Cube;
import ui.rendering.ShapeRenderer;
import ui.rendering.support.DrawingUtils;
import ui.rendering.support.Point3D;
import ui.rendering.support.SurfacePatch;
import ui.rendering.support.ThreeDimensionalRendererSupport;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Renders a cube as a subdivided shaded three-dimensional surface mesh.
 */
public class CubeRenderer implements ShapeRenderer<Cube> {

    private static final int DIVISIONS = 10;

    private static final double ROTATION_X = Math.toRadians(-60.0);
    private static final double ROTATION_Y = Math.toRadians(-30.0);
    private static final double ROTATION_Z = Math.toRadians(0.0);

    private static final int TOP = 55;
    private static final int AVAILABLE_WIDTH = 460;
    private static final int AVAILABLE_HEIGHT = 300;

    /**
     * Draws all six cube faces as a regular surface mesh.
     *
     * @param g2 graphics context used for rendering
     * @param cube cube to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Cube cube,
            int width,
            int height) {

        double side = cube.getSide();
        double half = side / 2.0;

        List<SurfacePatch> patches = new ArrayList<>();

        for (int face = 0; face < 6; face++) {
            for (int row = 0; row < DIVISIONS; row++) {
                for (int column = 0; column < DIVISIONS; column++) {

                    double a0 =
                            -half + side * column / DIVISIONS;
                    double a1 =
                            -half + side * (column + 1) / DIVISIONS;
                    double b0 =
                            -half + side * row / DIVISIONS;
                    double b1 =
                            -half + side * (row + 1) / DIVISIONS;

                    Point3D p0;
                    Point3D p1;
                    Point3D p2;
                    Point3D p3;

                    switch (face) {
                        case 0 -> {
                            p0 = new Point3D(a0, b0, -half);
                            p1 = new Point3D(a1, b0, -half);
                            p2 = new Point3D(a1, b1, -half);
                            p3 = new Point3D(a0, b1, -half);
                        }
                        case 1 -> {
                            p0 = new Point3D(a0, b0, half);
                            p1 = new Point3D(a0, b1, half);
                            p2 = new Point3D(a1, b1, half);
                            p3 = new Point3D(a1, b0, half);
                        }
                        case 2 -> {
                            p0 = new Point3D(a0, -half, b0);
                            p1 = new Point3D(a0, -half, b1);
                            p2 = new Point3D(a1, -half, b1);
                            p3 = new Point3D(a1, -half, b0);
                        }
                        case 3 -> {
                            p0 = new Point3D(half, a0, b0);
                            p1 = new Point3D(half, a1, b0);
                            p2 = new Point3D(half, a1, b1);
                            p3 = new Point3D(half, a0, b1);
                        }
                        case 4 -> {
                            p0 = new Point3D(a0, half, b0);
                            p1 = new Point3D(a1, half, b0);
                            p2 = new Point3D(a1, half, b1);
                            p3 = new Point3D(a0, half, b1);
                        }
                        default -> {
                            p0 = new Point3D(-half, a0, b0);
                            p1 = new Point3D(-half, a0, b1);
                            p2 = new Point3D(-half, a1, b1);
                            p3 = new Point3D(-half, a1, b0);
                        }
                    }

                    ThreeDimensionalRendererSupport.addSurfacePatch(
                            patches,
                            p0,
                            p1,
                            p2,
                            p3,
                            ROTATION_X,
                            ROTATION_Y,
                            ROTATION_Z);
                }
            }
        }

        double scale = Math.min(
                AVAILABLE_WIDTH * 0.52 / side,
                AVAILABLE_HEIGHT * 0.52 / side);

        ThreeDimensionalRendererSupport.drawSurfacePatches(
                g2,
                patches,
                width / 2.0,
                TOP + AVAILABLE_HEIGHT / 2.0,
                scale);

        g2.setColor(Color.BLACK);

        DrawingUtils.drawCenteredText(
                g2,
                "Side = " + DrawingUtils.format(side),
                width / 2.0,
                TOP + AVAILABLE_HEIGHT + 35);
    }
}
