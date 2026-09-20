package ui.rendering;

import geometry.Shape;

import java.awt.Graphics2D;

/**
 * Defines the contract used to render a supported geometric shape.
 *
 * @param <T> shape type handled by the renderer
 */
public interface ShapeRenderer<T extends Shape> {

    /**
     * Draws the supplied shape using the provided graphics context.
     *
     * @param g2 graphics context used for rendering
     * @param shape shape to render
     * @param width width of the available drawing panel
     * @param height height of the available drawing panel
     */
    void draw(
            Graphics2D g2,
            T shape,
            int width,
            int height);
}
