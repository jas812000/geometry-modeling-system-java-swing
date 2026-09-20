package ui;

import geometry.Shape;
import geometry.threedimensional.Cone;
import geometry.threedimensional.Cube;
import geometry.threedimensional.Cylinder;
import geometry.threedimensional.Sphere;
import geometry.threedimensional.Torus;
import geometry.twodimensional.Circle;
import geometry.twodimensional.Rectangle;
import geometry.twodimensional.Square;
import geometry.twodimensional.Triangle;
import ui.calculation.ShapeCalculationFormatter;
import ui.rendering.ShapePanel;
import ui.rendering.ShapeRendererDispatcher;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Displays the calculation summary and visualization for a geometric shape.
 *
 * <p>Rendering and calculation formatting are delegated to dedicated
 * components so this class is responsible only for constructing and managing
 * the visualization window.</p>
 */
public class ShapeDisplay {

    private static final int CALCULATION_PANEL_WIDTH = 270;
    private static final int DRAWING_HEIGHT = 400;

    private final JFrame frame;

    /**
     * Creates and displays a visualization for the supplied shape.
     *
     * @param shape shape to visualize
     * @throws IllegalArgumentException if the shape type is unsupported
     */
    public ShapeDisplay(Shape shape) {
        if (!ShapeRendererDispatcher.isSupported(shape)) {
            throw new IllegalArgumentException(
                    "Unsupported shape type: "
                            + (shape == null
                            ? "null"
                            : shape.getClass().getSimpleName()));
        }

        frame =
                new JFrame(getShapeName(shape) + " Visualization");

        createWindow(shape);
    }

    /**
     * Builds and displays the visualization window.
     *
     * @param shape shape being visualized
     */
    private void createWindow(Shape shape) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        JPanel contentPanel =
                new JPanel(new BorderLayout(10, 10));

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15));

        JLabel titleLabel = new JLabel(
                getShapeName(shape),
                SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22));

        contentPanel.add(
                titleLabel,
                BorderLayout.NORTH);

        JPanel resultPanel =
                new JPanel(new BorderLayout(25, 0));

        resultPanel.add(
                createCalculationPanel(shape),
                BorderLayout.WEST);

        resultPanel.add(
                new ShapePanel(shape),
                BorderLayout.CENTER);

        contentPanel.add(
                resultPanel,
                BorderLayout.CENTER);

        JButton closeButton =
                new JButton("Close");

        closeButton.addActionListener(
                e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        contentPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);

        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the calculation summary displayed beside the visualization.
     *
     * @param shape shape whose calculation is displayed
     * @return configured calculation panel
     */
    private JPanel createCalculationPanel(Shape shape) {
        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setPreferredSize(
                new Dimension(
                        CALCULATION_PANEL_WIDTH,
                        DRAWING_HEIGHT));

        javax.swing.border.TitledBorder calculationBorder =
                BorderFactory.createTitledBorder("Calculation");

        calculationBorder.setTitleFont(
                new Font("Arial", Font.BOLD, 20));

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        calculationBorder,
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15)));

        JLabel calculationLabel =
                new JLabel(
                        ShapeCalculationFormatter
                                .buildCalculationHtml(shape));

        calculationLabel.setFont(
                new Font("Arial", Font.PLAIN, 16));

        calculationLabel.setVerticalAlignment(
                SwingConstants.TOP);

        panel.add(
                calculationLabel,
                BorderLayout.NORTH);

        return panel;
    }

    /**
     * Returns the user-facing name of a supported shape.
     *
     * @param shape shape being displayed
     * @return shape display name
     * @throws IllegalArgumentException if the shape type is unsupported
     */
    private String getShapeName(Shape shape) {
        if (shape instanceof Circle) {
            return "Circle";
        }
        if (shape instanceof Rectangle) {
            return "Rectangle";
        }
        if (shape instanceof Square) {
            return "Square";
        }
        if (shape instanceof Triangle) {
            return "Triangle";
        }
        if (shape instanceof Sphere) {
            return "Sphere";
        }
        if (shape instanceof Cube) {
            return "Cube";
        }
        if (shape instanceof Cone) {
            return "Cone";
        }
        if (shape instanceof Cylinder) {
            return "Cylinder";
        }
        if (shape instanceof Torus) {
            return "Torus";
        }

        throw new IllegalArgumentException(
                "Unsupported shape type.");
    }
}
