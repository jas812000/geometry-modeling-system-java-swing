package ui.rendering.twodimensional;

import geometry.twodimensional.Rectangle;
import ui.rendering.ShapeRenderer;
import ui.rendering.support.DrawingUtils;
import ui.rendering.support.TwoDimensionalRendererSupport;
import ui.rendering.support.TwoDimensionalRendererSupport.ScaledDimension;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Renders a rectangle using its length-to-width ratio.
 */
public class RectangleRenderer implements ShapeRenderer<Rectangle> {

    /**
     * Draws a rectangle and labels its length and width.
     *
     * @param g2 graphics context used for rendering
     * @param rectangle rectangle to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Rectangle rectangle,
            int width,
            int height) {

        ScaledDimension scaled =
                TwoDimensionalRendererSupport.scale(
                        rectangle.getLength(),
                        rectangle.getWidth());

        double x =
                TwoDimensionalRendererSupport.centeredX(
                        width,
                        scaled.width());

        double y =
                TwoDimensionalRendererSupport.centeredY(
                        scaled.height());

        Rectangle2D shape = new Rectangle2D.Double(
                x,
                y,
                scaled.width(),
                scaled.height());

        TwoDimensionalRendererSupport.fillShape(
                g2,
                shape,
                y,
                y + scaled.height());

        DrawingUtils.drawHorizontalDimension(
                g2,
                x,
                y + scaled.height(),
                scaled.width(),
                "Length = "
                        + DrawingUtils.format(rectangle.getLength()));

        DrawingUtils.drawVerticalDimension(
                g2,
                x,
                y,
                scaled.height(),
                "Width = "
                        + DrawingUtils.format(rectangle.getWidth()));
    }
}
