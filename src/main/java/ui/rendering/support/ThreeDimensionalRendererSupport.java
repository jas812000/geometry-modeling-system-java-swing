package ui.rendering.support;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Comparator;
import java.util.List;

/**
 * Provides shared model-space transformations, lighting calculations, and
 * surface rendering operations for three-dimensional shape renderers.
 */
public final class ThreeDimensionalRendererSupport {

    private static final Point3D LIGHT_DIRECTION =
            normalize(new Point3D(-0.35, -0.55, 1.0));

    private ThreeDimensionalRendererSupport() {
        // Prevent instantiation because this class contains only shared helpers.
    }

    /**
     * Rotates and stores a four-point surface patch.
     *
     * @param patches destination collection
     * @param p0 first point
     * @param p1 second point
     * @param p2 third point
     * @param p3 fourth point
     * @param rotationX x-axis rotation in radians
     * @param rotationY y-axis rotation in radians
     * @param rotationZ z-axis rotation in radians
     */
    public static void addSurfacePatch(
            List<SurfacePatch> patches,
            Point3D p0,
            Point3D p1,
            Point3D p2,
            Point3D p3,
            double rotationX,
            double rotationY,
            double rotationZ) {

        p0 = rotate(p0, rotationX, rotationY, rotationZ);
        p1 = rotate(p1, rotationX, rotationY, rotationZ);
        p2 = rotate(p2, rotationX, rotationY, rotationZ);
        p3 = rotate(p3, rotationX, rotationY, rotationZ);

        Point3D normal = normalize(
                cross(
                        subtract(p1, p0),
                        subtract(p3, p0)));

        double depth =
                (p0.z() + p1.z() + p2.z() + p3.z()) / 4.0;

        patches.add(new SurfacePatch(
                new Point3D[]{p0, p1, p2, p3},
                depth,
                surfaceLight(normal)));
    }

    /**
     * Rotates and stores a three-point triangular surface patch.
     *
     * @param patches destination collection
     * @param p0 first point
     * @param p1 second point
     * @param p2 third point
     * @param rotationX x-axis rotation in radians
     * @param rotationY y-axis rotation in radians
     * @param rotationZ z-axis rotation in radians
     */
    public static void addTrianglePatch(
            List<SurfacePatch> patches,
            Point3D p0,
            Point3D p1,
            Point3D p2,
            double rotationX,
            double rotationY,
            double rotationZ) {

        p0 = rotate(p0, rotationX, rotationY, rotationZ);
        p1 = rotate(p1, rotationX, rotationY, rotationZ);
        p2 = rotate(p2, rotationX, rotationY, rotationZ);

        Point3D normal = normalize(
                cross(
                        subtract(p1, p0),
                        subtract(p2, p0)));

        double depth =
                (p0.z() + p1.z() + p2.z()) / 3.0;

        patches.add(new SurfacePatch(
                new Point3D[]{p0, p1, p2},
                depth,
                surfaceLight(normal)));
    }

    /**
     * Draws depth-sorted three-dimensional surface patches.
     *
     * @param g2 graphics context used for rendering
     * @param patches surface patches to draw
     * @param centerX horizontal projection center
     * @param centerY vertical projection center
     * @param scale model-to-screen scale
     */
    public static void drawSurfacePatches(
            Graphics2D g2,
            List<SurfacePatch> patches,
            double centerX,
            double centerY,
            double scale) {

        patches.sort(
                Comparator.comparingDouble(SurfacePatch::depth));

        g2.setStroke(new BasicStroke(0.55f));

        for (SurfacePatch patch : patches) {
            Path2D polygon = new Path2D.Double();

            for (int i = 0; i < patch.points().length; i++) {
                Point3D point = patch.points()[i];

                double screenX =
                        centerX + point.x() * scale;

                double screenY =
                        centerY - point.y() * scale;

                if (i == 0) {
                    polygon.moveTo(screenX, screenY);
                } else {
                    polygon.lineTo(screenX, screenY);
                }
            }

            polygon.closePath();

            double light = Math.max(
                    0.0,
                    Math.min(1.0, patch.light()));

            int red = (int) Math.round(
                    105 + light * 120);
            int green = (int) Math.round(
                    155 + light * 85);
            int blue = (int) Math.round(
                    190 + light * 60);

            red = Math.max(0, Math.min(255, red));
            green = Math.max(0, Math.min(255, green));
            blue = Math.max(0, Math.min(255, blue));

            g2.setColor(new Color(red, green, blue));
            g2.fill(polygon);

            g2.setColor(new Color(
                    Math.max(55, red - 50),
                    Math.max(75, green - 50),
                    Math.max(95, blue - 50)));

            g2.draw(polygon);
        }
    }

    /**
     * Rotates a point around the x, y, and z axes.
     *
     * @param point point to rotate
     * @param rotationX x-axis rotation in radians
     * @param rotationY y-axis rotation in radians
     * @param rotationZ z-axis rotation in radians
     * @return rotated point
     */
    public static Point3D rotate(
            Point3D point,
            double rotationX,
            double rotationY,
            double rotationZ) {

        double cosX = Math.cos(rotationX);
        double sinX = Math.sin(rotationX);

        double x1 = point.x();
        double y1 =
                point.y() * cosX
                        - point.z() * sinX;
        double z1 =
                point.y() * sinX
                        + point.z() * cosX;

        double cosY = Math.cos(rotationY);
        double sinY = Math.sin(rotationY);

        double x2 =
                x1 * cosY
                        + z1 * sinY;
        double y2 = y1;
        double z2 =
                -x1 * sinY
                        + z1 * cosY;

        double cosZ = Math.cos(rotationZ);
        double sinZ = Math.sin(rotationZ);

        return new Point3D(
                x2 * cosZ - y2 * sinZ,
                x2 * sinZ + y2 * cosZ,
                z2);
    }

    /**
     * Subtracts one three-dimensional vector from another.
     *
     * @param a first vector
     * @param b vector to subtract
     * @return resulting vector
     */
    public static Point3D subtract(Point3D a, Point3D b) {
        return new Point3D(
                a.x() - b.x(),
                a.y() - b.y(),
                a.z() - b.z());
    }

    /**
     * Calculates the cross product of two vectors.
     *
     * @param a first vector
     * @param b second vector
     * @return cross product
     */
    public static Point3D cross(Point3D a, Point3D b) {
        return new Point3D(
                a.y() * b.z() - a.z() * b.y(),
                a.z() * b.x() - a.x() * b.z(),
                a.x() * b.y() - a.y() * b.x());
    }

    /**
     * Calculates the dot product of two vectors.
     *
     * @param a first vector
     * @param b second vector
     * @return dot product
     */
    public static double dot(Point3D a, Point3D b) {
        return a.x() * b.x()
                + a.y() * b.y()
                + a.z() * b.z();
    }

    /**
     * Returns a unit-length version of a vector.
     *
     * @param point vector to normalize
     * @return normalized vector
     */
    public static Point3D normalize(Point3D point) {
        double length = Math.sqrt(
                point.x() * point.x()
                        + point.y() * point.y()
                        + point.z() * point.z());

        if (length == 0.0) {
            return new Point3D(0.0, 0.0, 0.0);
        }

        return new Point3D(
                point.x() / length,
                point.y() / length,
                point.z() / length);
    }

    /**
     * Calculates ambient and diffuse lighting for a surface normal.
     *
     * @param normal normalized surface normal
     * @return lighting intensity
     */
    private static double surfaceLight(Point3D normal) {
        double diffuse = Math.max(
                0.0,
                dot(normal, LIGHT_DIRECTION));

        return 0.55 + 0.45 * diffuse;
    }
}
