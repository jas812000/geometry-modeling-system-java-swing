package ui.rendering;

import geometry.Shape;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Swing drawing surface used to display a geometric shape.
 *
 * <p>The panel configures the Java2D graphics context and delegates the actual
 * shape drawing to {@link ShapeRendererDispatcher}.</p>
 */
public class ShapePanel extends JPanel {

    private static final int DRAWING_WIDTH = 540;
    private static final int DRAWING_HEIGHT = 400;

    private static final Font DIMENSION_FONT =
            new Font("Arial", Font.PLAIN, 15);

    private final Shape shape;

    /**
     * Creates a drawing panel for the supplied shape.
     *
     * @param shape shape to display
     */
    public ShapePanel(Shape shape) {
        if (!ShapeRendererDispatcher.isSupported(shape)) {
            throw new IllegalArgumentException(
                    "Unsupported shape type: "
                            + (shape == null
                            ? "null"
                            : shape.getClass().getSimpleName()));
        }

        this.shape = shape;

        setPreferredSize(
                new Dimension(DRAWING_WIDTH, DRAWING_HEIGHT));

        setBackground(Color.WHITE);
        setBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    /**
     * Configures antialiased Java2D rendering and delegates drawing to the
     * renderer registered for the current shape.
     *
     * @param graphics Swing graphics context
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 =
                (Graphics2D) graphics.create();

        try {
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2.setStroke(new BasicStroke(2.2f));
            g2.setFont(DIMENSION_FONT);
            g2.setColor(Color.BLACK);

            ShapeRendererDispatcher.draw(
                    g2,
                    shape,
                    getWidth(),
                    getHeight());

        } finally {
            g2.dispose();
        }
    }
}
