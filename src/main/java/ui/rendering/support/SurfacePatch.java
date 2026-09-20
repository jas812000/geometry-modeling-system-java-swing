package ui.rendering.support;

/**
 * Represents one polygonal surface patch used when rendering a
 * three-dimensional shape.
 *
 * <p>The depth value allows patches to be rendered back-to-front, while the
 * light value controls the shading applied to the surface.</p>
 *
 * @param points vertices that define the surface patch
 * @param depth average depth of the surface patch
 * @param light calculated lighting intensity
 */
public record SurfacePatch(
        Point3D[] points,
        double depth,
        double light) {
}
