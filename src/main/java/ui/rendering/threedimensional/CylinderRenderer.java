package ui.rendering.threedimensional;

import geometry.threedimensional.Cylinder;
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
 * Renders a cylinder as a shaded three-dimensional surface mesh.
 */
public class CylinderRenderer implements ShapeRenderer<Cylinder> {

    private static final int RADIAL_SEGMENTS = 56;
    private static final int HEIGHT_SEGMENTS = 12;
    private static final int CAP_SEGMENTS = 10;

    private static final double ROTATION_X = Math.toRadians(-60.0);
    private static final double ROTATION_Y = Math.toRadians(-10.0);
    private static final double ROTATION_Z = Math.toRadians(0.0);

    private static final int TOP = 55;
    private static final int AVAILABLE_WIDTH = 460;
    private static final int AVAILABLE_HEIGHT = 300;

    /**
     * Draws the cylinder wall and both circular caps as surface patches.
     *
     * @param g2 graphics context used for rendering
     * @param cylinder cylinder to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Cylinder cylinder,
            int width,
            int height) {

        double radius = cylinder.getRadius();
        double cylinderHeight = cylinder.getHeight();

        List<SurfacePatch> patches = new ArrayList<>();

        for (int i = 0; i < RADIAL_SEGMENTS; i++) {
            double angle0 =
                    2.0 * Math.PI * i / RADIAL_SEGMENTS;
            double angle1 =
                    2.0 * Math.PI * (i + 1) / RADIAL_SEGMENTS;

            for (int row = 0; row < HEIGHT_SEGMENTS; row++) {
                double z0 =
                        -cylinderHeight / 2.0
                                + cylinderHeight * row
                                / HEIGHT_SEGMENTS;

                double z1 =
                        -cylinderHeight / 2.0
                                + cylinderHeight * (row + 1)
                                / HEIGHT_SEGMENTS;

                Point3D p0 = new Point3D(
                        radius * Math.cos(angle0),
                        radius * Math.sin(angle0),
                        z0);

                Point3D p1 = new Point3D(
                        radius * Math.cos(angle1),
                        radius * Math.sin(angle1),
                        z0);

                Point3D p2 = new Point3D(
                        radius * Math.cos(angle1),
                        radius * Math.sin(angle1),
                        z1);

                Point3D p3 = new Point3D(
                        radius * Math.cos(angle0),
                        radius * Math.sin(angle0),
                        z1);

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

            for (int ring = 0; ring < CAP_SEGMENTS; ring++) {
                double inner =
                        radius * ring / CAP_SEGMENTS;
                double outer =
                        radius * (ring + 1) / CAP_SEGMENTS;

                Point3D top0 = new Point3D(
                        inner * Math.cos(angle0),
                        inner * Math.sin(angle0),
                        cylinderHeight / 2.0);

                Point3D top1 = new Point3D(
                        outer * Math.cos(angle0),
                        outer * Math.sin(angle0),
                        cylinderHeight / 2.0);

                Point3D top2 = new Point3D(
                        outer * Math.cos(angle1),
                        outer * Math.sin(angle1),
                        cylinderHeight / 2.0);

                Point3D top3 = new Point3D(
                        inner * Math.cos(angle1),
                        inner * Math.sin(angle1),
                        cylinderHeight / 2.0);

                ThreeDimensionalRendererSupport.addSurfacePatch(
                        patches,
                        top0,
                        top1,
                        top2,
                        top3,
                        ROTATION_X,
                        ROTATION_Y,
                        ROTATION_Z);

                Point3D bottom0 = new Point3D(
                        inner * Math.cos(angle0),
                        inner * Math.sin(angle0),
                        -cylinderHeight / 2.0);

                Point3D bottom1 = new Point3D(
                        inner * Math.cos(angle1),
                        inner * Math.sin(angle1),
                        -cylinderHeight / 2.0);

                Point3D bottom2 = new Point3D(
                        outer * Math.cos(angle1),
                        outer * Math.sin(angle1),
                        -cylinderHeight / 2.0);

                Point3D bottom3 = new Point3D(
                        outer * Math.cos(angle0),
                        outer * Math.sin(angle0),
                        -cylinderHeight / 2.0);

                ThreeDimensionalRendererSupport.addSurfacePatch(
                        patches,
                        bottom0,
                        bottom1,
                        bottom2,
                        bottom3,
                        ROTATION_X,
                        ROTATION_Y,
                        ROTATION_Z);
            }
        }

        double diameter = radius * 2.0;

        double scale = Math.min(
                AVAILABLE_WIDTH * 0.62 / diameter,
                AVAILABLE_HEIGHT * 0.62 / cylinderHeight);

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
                        + DrawingUtils.format(cylinderHeight),
                width / 2.0,
                TOP + AVAILABLE_HEIGHT + 35);
    }
}
