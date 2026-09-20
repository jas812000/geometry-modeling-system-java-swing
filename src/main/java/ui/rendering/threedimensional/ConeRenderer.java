package ui.rendering.threedimensional;

import geometry.threedimensional.Cone;
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
 * Renders a cone as a shaded three-dimensional surface mesh.
 */
public class ConeRenderer implements ShapeRenderer<Cone> {

    private static final int SEGMENTS = 56;

    private static final double ROTATION_X = Math.toRadians(110.0);
    private static final double ROTATION_Y = Math.toRadians(-10.0);
    private static final double ROTATION_Z = Math.toRadians(0.0);

    private static final int TOP = 55;
    private static final int AVAILABLE_WIDTH = 460;
    private static final int AVAILABLE_HEIGHT = 300;

    /**
     * Draws the cone's curved surface and circular base.
     *
     * @param g2 graphics context used for rendering
     * @param cone cone to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Cone cone,
            int width,
            int height) {

        double radius = cone.getRadius();
        double coneHeight = cone.getHeight();

        List<SurfacePatch> patches = new ArrayList<>();

        Point3D apex =
                new Point3D(0.0, 0.0, coneHeight / 2.0);

        Point3D baseCenter =
                new Point3D(0.0, 0.0, -coneHeight / 2.0);

        for (int i = 0; i < SEGMENTS; i++) {
            double angle0 =
                    2.0 * Math.PI * i / SEGMENTS;

            double angle1 =
                    2.0 * Math.PI * (i + 1) / SEGMENTS;

            Point3D base0 = new Point3D(
                    radius * Math.cos(angle0),
                    radius * Math.sin(angle0),
                    -coneHeight / 2.0);

            Point3D base1 = new Point3D(
                    radius * Math.cos(angle1),
                    radius * Math.sin(angle1),
                    -coneHeight / 2.0);

            ThreeDimensionalRendererSupport.addTrianglePatch(
                    patches,
                    apex,
                    base0,
                    base1,
                    ROTATION_X,
                    ROTATION_Y,
                    ROTATION_Z);

            ThreeDimensionalRendererSupport.addTrianglePatch(
                    patches,
                    baseCenter,
                    base1,
                    base0,
                    ROTATION_X,
                    ROTATION_Y,
                    ROTATION_Z);
        }

        double diameter = radius * 2.0;

        double scale = Math.min(
                AVAILABLE_WIDTH * 0.62 / diameter,
                AVAILABLE_HEIGHT * 0.62 / coneHeight);

        ThreeDimensionalRendererSupport.drawSurfacePatches(
                g2,
                patches,
                width / 2.0,
                TOP + AVAILABLE_HEIGHT / 2.0,
                scale);

        g2.setColor(Color.BLACK);

        DrawingUtils.drawCenteredText(
                g2,
                "Radius = " + DrawingUtils.format(radius)
                        + "    Height = "
                        + DrawingUtils.format(coneHeight),
                width / 2.0,
                TOP + AVAILABLE_HEIGHT + 35);
    }
}
