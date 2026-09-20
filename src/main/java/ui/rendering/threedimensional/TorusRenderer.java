package ui.rendering.threedimensional;

import geometry.threedimensional.Torus;
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
 * Renders a torus from its three-dimensional parametric surface.
 */
public class TorusRenderer implements ShapeRenderer<Torus> {

    private static final int MAJOR_SEGMENTS = 64;
    private static final int MINOR_SEGMENTS = 28;

    private static final double ROTATION_X = Math.toRadians(-50.0);
    private static final double ROTATION_Y = Math.toRadians(-10.0);
    private static final double ROTATION_Z = Math.toRadians(0.0);

    private static final int TOP = 55;
    private static final int AVAILABLE_WIDTH = 460;
    private static final int AVAILABLE_HEIGHT = 300;

    /**
     * Generates, rotates, and renders the torus surface.
     *
     * @param g2 graphics context used for rendering
     * @param torus torus to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Torus torus,
            int width,
            int height) {

        double majorRadius = torus.getMajorRadius();
        double minorRadius = torus.getMinorRadius();

        double modelRadius = majorRadius + minorRadius;

        double scale = Math.min(
                AVAILABLE_WIDTH * 0.42 / modelRadius,
                AVAILABLE_HEIGHT * 0.42 / modelRadius);

        double centerX = width / 2.0;
        double centerY = TOP + AVAILABLE_HEIGHT / 2.0;

        List<SurfacePatch> patches = new ArrayList<>();

        for (int i = 0; i < MAJOR_SEGMENTS; i++) {
            double u0 =
                    2.0 * Math.PI * i / MAJOR_SEGMENTS;
            double u1 =
                    2.0 * Math.PI * (i + 1) / MAJOR_SEGMENTS;

            for (int j = 0; j < MINOR_SEGMENTS; j++) {
                double v0 =
                        2.0 * Math.PI * j / MINOR_SEGMENTS;
                double v1 =
                        2.0 * Math.PI * (j + 1) / MINOR_SEGMENTS;

                Point3D p0 =
                        torusPoint(
                                majorRadius,
                                minorRadius,
                                u0,
                                v0);

                Point3D p1 =
                        torusPoint(
                                majorRadius,
                                minorRadius,
                                u1,
                                v0);

                Point3D p2 =
                        torusPoint(
                                majorRadius,
                                minorRadius,
                                u1,
                                v1);

                Point3D p3 =
                        torusPoint(
                                majorRadius,
                                minorRadius,
                                u0,
                                v1);

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

        ThreeDimensionalRendererSupport.drawSurfacePatches(
                g2,
                patches,
                centerX,
                centerY,
                scale);

        g2.setColor(Color.BLACK);

        DrawingUtils.drawCenteredText(
                g2,
                "Major radius = "
                        + DrawingUtils.format(majorRadius)
                        + "    Minor radius = "
                        + DrawingUtils.format(minorRadius),
                width / 2.0,
                TOP + AVAILABLE_HEIGHT + 35);
    }

    /**
     * Calculates one point on the torus parametric surface.
     */
    private Point3D torusPoint(
            double majorRadius,
            double minorRadius,
            double u,
            double v) {

        double ring =
                majorRadius + minorRadius * Math.cos(v);

        return new Point3D(
                ring * Math.cos(u),
                ring * Math.sin(u),
                minorRadius * Math.sin(v));
    }
}
