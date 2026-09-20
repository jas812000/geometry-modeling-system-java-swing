package ui.rendering.support;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Provides shared drawing and formatting operations used by shape renderers.
 */
public final class DrawingUtils {

    private DrawingUtils() {
        // Prevent instantiation because this class contains only shared helpers.
    }

    /**
     * Draws text centered around the supplied x-coordinate.
     *
     * @param g2 graphics context used for rendering
     * @param text text to draw
     * @param centerX horizontal center position
     * @param y vertical baseline position
     */
    public static void drawCenteredText(
            Graphics2D g2,
            String text,
            double centerX,
            double y) {

        FontMetrics metrics = g2.getFontMetrics();
        double x = centerX - metrics.stringWidth(text) / 2.0;

        drawText(g2, text, x, y);
    }

    /**
     * Draws text at the supplied coordinates.
     *
     * @param g2 graphics context used for rendering
     * @param text text to draw
     * @param x horizontal position
     * @param y vertical baseline position
     */
    public static void drawText(
            Graphics2D g2,
            String text,
            double x,
            double y) {

        g2.drawString(
                text,
                (float) x,
                (float) y);
    }

    /**
     * Draws a horizontal dimension label beneath a measurement.
     *
     * @param g2 graphics context used for rendering
     * @param x starting horizontal coordinate
     * @param y vertical coordinate of the measured edge
     * @param width displayed width of the measurement
     * @param label dimension label
     */
    public static void drawHorizontalDimension(
            Graphics2D g2,
            double x,
            double y,
            double width,
            String label) {

        double labelX = x + width / 2.0;
        drawCenteredText(g2, label, labelX, y + 28);
    }

    /**
     * Draws a vertical dimension label beside a measurement.
     *
     * @param g2 graphics context used for rendering
     * @param x horizontal coordinate of the measured edge
     * @param y starting vertical coordinate
     * @param height displayed height of the measurement
     * @param label dimension label
     */
    public static void drawVerticalDimension(
            Graphics2D g2,
            double x,
            double y,
            double height,
            String label) {

        drawText(
                g2,
                label,
                Math.max(8, x - 75),
                y + height / 2.0);
    }

    /**
     * Formats a numeric dimension without unnecessary trailing zeros.
     *
     * @param value value to format
     * @return formatted value
     */
    public static String format(double value) {
        if (value == Math.rint(value)) {
            return String.format("%.0f", value);
        }

        return String.format("%.2f", value);
    }
}
