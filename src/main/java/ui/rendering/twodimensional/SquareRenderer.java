package ui.rendering.twodimensional;

import geometry.twodimensional.Square;
import ui.rendering.ShapeRenderer;
import ui.rendering.support.DrawingUtils;
import ui.rendering.support.TwoDimensionalRendererSupport;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Renders a square and its side measurement.
 */
public class SquareRenderer implements ShapeRenderer<Square> {

    /**
     * Draws a square proportionally within the available drawing area.
     *
     * @param g2 graphics context used for rendering
     * @param square square to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Square square,
            int width,
            int height) {

        double size =
                TwoDimensionalRendererSupport.scaleUniform(
                        square.getSide(),
                        square.getSide());

        double x =
                TwoDimensionalRendererSupport.centeredX(
                        width,
                        size);

        double y =
                TwoDimensionalRendererSupport.centeredY(size);

        Rectangle2D shape =
                new Rectangle2D.Double(x, y, size, size);

        TwoDimensionalRendererSupport.fillShape(
                g2,
                shape,
                y,
                y + size);

        DrawingUtils.drawHorizontalDimension(
                g2,
                x,
                y + size,
                size,
                "Side = "
                        + DrawingUtils.format(square.getSide()));
    }
}
