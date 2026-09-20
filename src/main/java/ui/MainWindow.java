package ui;

import ui.workflow.ThreeDimensionalShapeWorkflow;
import ui.workflow.TwoDimensionalShapeWorkflow;

import javax.swing.*;
import java.awt.*;

/**
 * Builds and manages the main Geometry Modeling application window.
 *
 * <p>The window presents the available two-dimensional and
 * three-dimensional shapes and delegates each selection to the appropriate
 * workflow.</p>
 */
public class MainWindow {

    private static final Font TITLE_FONT =
            new Font("Arial", Font.BOLD, 22);

    private static final Font MENU_FONT =
            new Font("Arial", Font.BOLD, 20);

    private static final Font OPTION_FONT =
            new Font("Arial", Font.PLAIN, 18);

    private final JFrame frame;

    private final TwoDimensionalShapeWorkflow
            twoDimensionalWorkflow;

    private final ThreeDimensionalShapeWorkflow
            threeDimensionalWorkflow;

    /**
     * Creates and displays the main application window.
     */
    public MainWindow() {
        frame =
                new JFrame("Geometry Modeling Application");

        twoDimensionalWorkflow =
                new TwoDimensionalShapeWorkflow(frame);

        threeDimensionalWorkflow =
                new ThreeDimensionalShapeWorkflow(frame);

        createWindow();
    }

    /**
     * Builds and displays the main shape-selection interface.
     */
    private void createWindow() {
        frame.setDefaultCloseOperation(
                JFrame.DO_NOTHING_ON_CLOSE);

        frame.setResizable(false);

        frame.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent event) {

                        exitApplication();
                    }
                });

        JPanel mainPanel = new JPanel();
        mainPanel.setFocusable(true);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        25,
                        35));

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS));

        JLabel titleLabel =
                new JLabel(
                        "Welcome to Geometry Modeling",
                        SwingConstants.CENTER);

        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel =
                new JLabel(
                        "<html><div style='text-align: center;'>"
                                + "Explore common 2D and 3D shapes while "
                                + "calculating their areas and volumes."
                                + "<br><br>"
                                + "<b>Choose a shape to get started</b>"
                                + "</div></html>",
                        SwingConstants.CENTER);

        subtitleLabel.setFont(OPTION_FONT);
        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        JPanel shapePanel = new JPanel();

        shapePanel.setLayout(
                new BoxLayout(
                        shapePanel,
                        BoxLayout.X_AXIS));

        shapePanel.add(
                createTwoDimensionalPanel());

        shapePanel.add(
                Box.createHorizontalStrut(50));

        shapePanel.add(
                createThreeDimensionalPanel());

        JButton exitButton =
                new JButton("Exit");

        exitButton.setFont(OPTION_FONT);

        exitButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        exitButton.setPreferredSize(
                new Dimension(120, 35));

        exitButton.setMaximumSize(
                new Dimension(120, 35));

        exitButton.addActionListener(
                event -> exitApplication());

        mainPanel.add(titleLabel);
        mainPanel.add(
                Box.createVerticalStrut(8));

        mainPanel.add(subtitleLabel);
        mainPanel.add(
                Box.createVerticalStrut(25));

        mainPanel.add(shapePanel);
        mainPanel.add(
                Box.createVerticalStrut(25));

        mainPanel.add(exitButton);

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainPanel.requestFocusInWindow();
    }

    /**
     * Creates the menu containing the two-dimensional shape options.
     *
     * @return configured two-dimensional shape panel
     */
    private JPanel createTwoDimensionalPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS));

        JLabel label =
                new JLabel("2D Shapes");

        label.setFont(MENU_FONT);
        label.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(
                Box.createVerticalStrut(12));

        panel.add(
                createShapeButton(
                        "Circle",
                        twoDimensionalWorkflow
                                ::showCircleDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Rectangle",
                        twoDimensionalWorkflow
                                ::showRectangleDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Square",
                        twoDimensionalWorkflow
                                ::showSquareDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Triangle",
                        twoDimensionalWorkflow
                                ::showTriangleMenu));

        return panel;
    }

    /**
     * Creates the menu containing the three-dimensional shape options.
     *
     * @return configured three-dimensional shape panel
     */
    private JPanel createThreeDimensionalPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS));

        JLabel label =
                new JLabel("3D Shapes");

        label.setFont(MENU_FONT);
        label.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(
                Box.createVerticalStrut(12));

        panel.add(
                createShapeButton(
                        "Sphere",
                        threeDimensionalWorkflow
                                ::showSphereDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Cube",
                        threeDimensionalWorkflow
                                ::showCubeDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Cone",
                        threeDimensionalWorkflow
                                ::showConeDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Cylinder",
                        threeDimensionalWorkflow
                                ::showCylinderDialog));

        panel.add(
                Box.createVerticalStrut(8));

        panel.add(
                createShapeButton(
                        "Torus",
                        threeDimensionalWorkflow
                                ::showTorusDialog));

        return panel;
    }

    /**
     * Creates a consistently styled shape-selection button.
     *
     * @param text button label
     * @param action workflow executed when selected
     * @return configured button
     */
    private JButton createShapeButton(
            String text,
            Runnable action) {

        JButton button =
                new JButton(text);

        button.setFont(OPTION_FONT);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        button.setPreferredSize(
                new Dimension(160, 38));

        button.setMaximumSize(
                new Dimension(160, 38));

        button.addActionListener(
                event -> action.run());

        return button;
    }

    /**
     * Confirms whether the user wants to exit the application.
     */
    private void exitApplication() {
        int choice =
                JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit Geometry Modeling?",
                        "Exit Geometry Modeling",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
        }
    }
}
