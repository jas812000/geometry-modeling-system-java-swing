package ui.rendering.twodimensional;

import geometry.twodimensional.Triangle;
import ui.rendering.ShapeRenderer;
import ui.rendering.support.DrawingUtils;
import ui.rendering.support.TwoDimensionalRendererSupport;
import ui.rendering.support.TwoDimensionalRendererSupport.ScaledDimension;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Renders triangles defined either by base and height or by three side
 * lengths.
 */
public class TriangleRenderer implements ShapeRenderer<Triangle> {

    /**
     * Selects the appropriate triangle visualization based on how the
     * triangle was constructed.
     *
     * @param g2 graphics context used for rendering
     * @param triangle triangle to render
     * @param width width of the drawing panel
     * @param height height of the drawing panel
     */
    @Override
    public void draw(
            Graphics2D g2,
            Triangle triangle,
            int width,
            int height) {

        if (triangle.isDefinedByThreeSides()) {
            drawThreeSideTriangle(g2, triangle, width);
        } else {
            drawBaseHeightTriangle(g2, triangle, width);
        }
    }

    /**
     * Draws a triangle defined by its base and height.
     */
    private void drawBaseHeightTriangle(
            Graphics2D g2,
            Triangle triangle,
            int panelWidth) {

        ScaledDimension scaled =
                TwoDimensionalRendererSupport.scale(
                        triangle.getBase(),
                        triangle.getHeight());

        double x =
                TwoDimensionalRendererSupport.centeredX(
                        panelWidth,
                        scaled.width());

        double y =
                TwoDimensionalRendererSupport.centeredY(
                        scaled.height());

        double leftX = x;
        double rightX = x + scaled.width();
        double bottomY = y + scaled.height();
        double apexX = x + scaled.width() / 2.0;

        Path2D path = new Path2D.Double();
        path.moveTo(leftX, bottomY);
        path.lineTo(rightX, bottomY);
        path.lineTo(apexX, y);
        path.closePath();

        TwoDimensionalRendererSupport.fillShape(
                g2,
                path,
                y,
                bottomY);

        g2.setColor(new Color(105, 105, 105));
        g2.setStroke(new BasicStroke(
                1.2f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f,
                new float[]{6.0f, 5.0f},
                0.0f));

        g2.draw(new Line2D.Double(
                apexX,
                y,
                apexX,
                bottomY));

        g2.setStroke(new BasicStroke(2.2f));
        g2.setColor(Color.BLACK);

        DrawingUtils.drawHorizontalDimension(
                g2,
                leftX,
                bottomY,
                scaled.width(),
                "Base = "
                        + DrawingUtils.format(triangle.getBase()));

        DrawingUtils.drawText(
                g2,
                "Height = "
                        + DrawingUtils.format(triangle.getHeight()),
                apexX + 10,
                y + scaled.height() / 2.0);
    }

    /**
     * Draws a triangle whose proportions are derived from three side lengths.
     */
    private void drawThreeSideTriangle(
            Graphics2D g2,
            Triangle triangle,
            int panelWidth) {

        double a = triangle.getSideA();
        double b = triangle.getSideB();
        double c = triangle.getSideC();

        double apexXFromLeft =
                (b * b + c * c - a * a) / (2.0 * c);

        double rawHeight = Math.sqrt(
                Math.max(
                        0.0,
                        b * b
                                - apexXFromLeft * apexXFromLeft));

        double scale = Math.min(
                TwoDimensionalRendererSupport.AVAILABLE_WIDTH / c,
                TwoDimensionalRendererSupport.AVAILABLE_HEIGHT
                        / rawHeight);

        double baseWidth = c * scale;
        double height = rawHeight * scale;
        double apexOffset = apexXFromLeft * scale;

        double x =
                TwoDimensionalRendererSupport.centeredX(
                        panelWidth,
                        baseWidth);

        double y =
                TwoDimensionalRendererSupport.centeredY(height);

        double bottomY = y + height;
        double leftX = x;
        double rightX = x + baseWidth;
        double apexX = x + apexOffset;

        Path2D path = new Path2D.Double();
        path.moveTo(leftX, bottomY);
        path.lineTo(rightX, bottomY);
        path.lineTo(apexX, y);
        path.closePath();

        TwoDimensionalRendererSupport.fillShape(
                g2,
                path,
                y,
                bottomY);

        g2.setColor(Color.BLACK);

        DrawingUtils.drawText(
                g2,
                "A = " + DrawingUtils.format(a),
                (rightX + apexX) / 2.0 + 8,
                (bottomY + y) / 2.0);

        DrawingUtils.drawText(
                g2,
                "B = " + DrawingUtils.format(b),
                (leftX + apexX) / 2.0 - 65,
                (bottomY + y) / 2.0);

        DrawingUtils.drawHorizontalDimension(
                g2,
                leftX,
                bottomY,
                baseWidth,
                "C = " + DrawingUtils.format(c));

        DrawingUtils.drawCenteredText(
                g2,
                triangle.determineTriangleType(),
                panelWidth / 2.0,
                TwoDimensionalRendererSupport.TOP
                        + TwoDimensionalRendererSupport.AVAILABLE_HEIGHT
                        + 70);
    }
}
