package ui.rendering.twodimensional;

import geometry.twodimensional.Circle;
import ui.rendering.ShapeRenderer;
import ui.rendering.support.DrawingUtils;
import ui.rendering.support.TwoDimensionalRendererSupport;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Renders a circle and its radius measurement.
 */
public class CircleRenderer implements ShapeRenderer<Circle> {

    /**
     * Draws a circle proportionally within the available drawing area.
     *
     * @param g2 graphics context used for rendering
     * @param circle circle to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Circle circle,
            int width,
            int height) {

        double diameter = circle.getRadius() * 2.0;

        double size =
                TwoDimensionalRendererSupport.scaleUniform(
                        diameter,
                        diameter);

        double x =
                TwoDimensionalRendererSupport.centeredX(
                        width,
                        size);

        double y =
                TwoDimensionalRendererSupport.centeredY(size);

        Ellipse2D shape =
                new Ellipse2D.Double(x, y, size, size);

        TwoDimensionalRendererSupport.fillShape(
                g2,
                shape,
                y,
                y + size);

        double centerX = x + size / 2.0;
        double centerY = y + size / 2.0;

        g2.setColor(new Color(95, 95, 95));
        g2.setStroke(new BasicStroke(1.4f));

        g2.draw(new Line2D.Double(
                centerX,
                centerY,
                x + size,
                centerY));

        g2.fill(new Ellipse2D.Double(
                centerX - 2.5,
                centerY - 2.5,
                5.0,
                5.0));

        DrawingUtils.drawCenteredText(
                g2,
                "Radius = "
                        + DrawingUtils.format(circle.getRadius()),
                centerX + size / 4.0,
                centerY - 10);
    }
}
