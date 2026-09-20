package ui.rendering.support;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;

/**
 * Provides shared sizing, positioning, and styling operations for
 * two-dimensional shape renderers.
 */
public final class TwoDimensionalRendererSupport {

    public static final int LEFT = 80;
    public static final int TOP = 55;
    public static final int AVAILABLE_WIDTH = 460;
    public static final int AVAILABLE_HEIGHT = 300;

    private TwoDimensionalRendererSupport() {
        // Prevent instantiation because this class contains only shared helpers.
    }

    /**
     * Scales two logical dimensions proportionally to fit the drawing area.
     *
     * @param width logical width
     * @param height logical height
     * @return scaled dimensions
     */
    public static ScaledDimension scale(
            double width,
            double height) {

        double scale = Math.min(
                AVAILABLE_WIDTH / width,
                AVAILABLE_HEIGHT / height);

        return new ScaledDimension(
                width * scale,
                height * scale);
    }

    /**
     * Returns the largest uniform size that fits the drawing area.
     *
     * @param width logical width
     * @param height logical height
     * @return scaled uniform size
     */
    public static double scaleUniform(
            double width,
            double height) {

        ScaledDimension scaled = scale(width, height);
        return Math.min(scaled.width(), scaled.height());
    }

    /**
     * Calculates the horizontal coordinate needed to center a drawing.
     *
     * @param panelWidth current panel width
     * @param width drawing width
     * @return centered x-coordinate
     */
    public static double centeredX(
            int panelWidth,
            double width) {

        return (panelWidth - width) / 2.0;
    }

    /**
     * Calculates the vertical coordinate needed to center a drawing within
     * the primary drawing area.
     *
     * @param height drawing height
     * @return centered y-coordinate
     */
    public static double centeredY(double height) {
        return TOP + (AVAILABLE_HEIGHT - height) / 2.0;
    }

    /**
     * Fills a two-dimensional shape with the application's gradient and draws
     * its outline.
     *
     * @param g2 graphics context used for rendering
     * @param shape shape to fill and outline
     * @param top top coordinate of the shape
     * @param bottom bottom coordinate of the shape
     */
    public static void fillShape(
            Graphics2D g2,
            java.awt.Shape shape,
            double top,
            double bottom) {

        Paint previousPaint = g2.getPaint();
        java.awt.Stroke previousStroke = g2.getStroke();

        GradientPaint gradient = new GradientPaint(
                0.0f,
                (float) top,
                new Color(225, 240, 250),
                0.0f,
                (float) bottom,
                new Color(155, 195, 220));

        g2.setPaint(gradient);
        g2.fill(shape);

        g2.setPaint(new Color(65, 95, 115));
        g2.setStroke(new BasicStroke(1.6f));
        g2.draw(shape);

        g2.setPaint(previousPaint);
        g2.setStroke(previousStroke);
    }

    /**
     * Holds scaled drawing dimensions without relying on integer-based AWT
     * dimension classes.
     *
     * @param width scaled width
     * @param height scaled height
     */
    public record ScaledDimension(
            double width,
            double height) {
    }
}
