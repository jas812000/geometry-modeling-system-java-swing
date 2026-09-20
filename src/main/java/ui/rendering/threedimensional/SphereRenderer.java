package ui.rendering.threedimensional;

import geometry.threedimensional.Sphere;
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
 * Renders a sphere as a shaded three-dimensional surface mesh.
 */
public class SphereRenderer implements ShapeRenderer<Sphere> {

    private static final int LATITUDE_SEGMENTS = 24;
    private static final int LONGITUDE_SEGMENTS = 48;

    private static final double ROTATION_X = Math.toRadians(-50.0);
    private static final double ROTATION_Y = Math.toRadians(-10.0);
    private static final double ROTATION_Z = Math.toRadians(0.0);

    private static final int TOP = 55;
    private static final int AVAILABLE_WIDTH = 460;
    private static final int AVAILABLE_HEIGHT = 300;

    /**
     * Draws a sphere using a depth-sorted surface mesh.
     *
     * @param g2 graphics context used for rendering
     * @param sphere sphere to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Sphere sphere,
            int width,
            int height) {

        double radius = sphere.getRadius();

        double scale = Math.min(
                AVAILABLE_WIDTH * 0.38 / radius,
                AVAILABLE_HEIGHT * 0.38 / radius);

        double centerX = width / 2.0;
        double centerY = TOP + AVAILABLE_HEIGHT / 2.0;

        List<SurfacePatch> patches = new ArrayList<>();

        for (int latitude = 0;
             latitude < LATITUDE_SEGMENTS;
             latitude++) {

            double phi0 =
                    -Math.PI / 2.0
                            + Math.PI * latitude / LATITUDE_SEGMENTS;

            double phi1 =
                    -Math.PI / 2.0
                            + Math.PI * (latitude + 1)
                            / LATITUDE_SEGMENTS;

            for (int longitude = 0;
                 longitude < LONGITUDE_SEGMENTS;
                 longitude++) {

                double theta0 =
                        2.0 * Math.PI * longitude
                                / LONGITUDE_SEGMENTS;

                double theta1 =
                        2.0 * Math.PI * (longitude + 1)
                                / LONGITUDE_SEGMENTS;

                Point3D p0 = spherePoint(radius, phi0, theta0);
                Point3D p1 = spherePoint(radius, phi0, theta1);
                Point3D p2 = spherePoint(radius, phi1, theta1);
                Point3D p3 = spherePoint(radius, phi1, theta0);

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
                "Radius = " + DrawingUtils.format(radius),
                width / 2.0,
                TOP + AVAILABLE_HEIGHT + 35);
    }

    /**
     * Calculates one point on the spherical surface.
     */
    private Point3D spherePoint(
            double radius,
            double latitude,
            double longitude) {

        double cosLatitude = Math.cos(latitude);

        return new Point3D(
                radius * cosLatitude * Math.cos(longitude),
                radius * cosLatitude * Math.sin(longitude),
                radius * Math.sin(latitude));
    }
}
