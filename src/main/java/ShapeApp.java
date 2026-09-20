import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

/**
 * Provides the graphical user interface for the Geometry Modeling
 * application.
 *
 * <p>The application allows users to construct two-dimensional and
 * three-dimensional shapes, enter their dimensions, calculate area or
 * volume, and display a dimension-aware visualization of the selected
 * shape.</p>
 */
public class ShapeApp {

    private static final Font TITLE_FONT =
            new Font("Arial", Font.BOLD, 22);

    private static final Font MENU_FONT =
            new Font("Arial", Font.BOLD, 20);

    private static final Font OPTION_FONT =
            new Font("Arial", Font.PLAIN, 18);

    private static final Dimension FIELD_SIZE =
            new Dimension(200, 25);

    private final JFrame mainFrame;


    /**
     * Creates and displays the main application window.
     */
    public ShapeApp() {
        mainFrame = new JFrame("Geometry Modeling Application");

        createMainWindow();
    }
    /**
     * Creates and displays the main shape-selection window.
     */
    private void createMainWindow() {

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setFocusable(true);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35));
        mainPanel.setLayout(
                new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(
                "Welcome to Geometry Modeling",
                SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Explore common 2D and 3D shapes while calculating "
                        + "their areas and volumes."
                        + "<br><br>"
                        + "<b>Choose a shape to get started</b>"
                        + "</div></html>",
                SwingConstants.CENTER);
        subtitleLabel.setFont(OPTION_FONT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(
                new BoxLayout(shapePanel, BoxLayout.X_AXIS));

        shapePanel.add(createTwoDimensionalPanel());
        shapePanel.add(Box.createHorizontalStrut(50));
        shapePanel.add(createThreeDimensionalPanel());

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(OPTION_FONT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setPreferredSize(new Dimension(120, 35));
        exitButton.setMaximumSize(new Dimension(120, 35));
        exitButton.addActionListener(e -> exitApplication());

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(shapePanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(exitButton);

        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainPanel.requestFocusInWindow();
    }

    /**
     * Creates the panel containing the two-dimensional shape options.
     *
     * @return configured two-dimensional shape panel
     */
    private JPanel createTwoDimensionalPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("2D Shapes");
        label.setFont(MENU_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(12));
        panel.add(createShapeButton("Circle", this::showCircleDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Rectangle", this::showRectangleDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Square", this::showSquareDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Triangle", this::showTriangleMenu));

        return panel;
    }

    /**
     * Creates the panel containing the three-dimensional shape options.
     *
     * @return configured three-dimensional shape panel
     */
    private JPanel createThreeDimensionalPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("3D Shapes");
        label.setFont(MENU_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(12));
        panel.add(createShapeButton("Sphere", this::showSphereDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Cube", this::showCubeDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Cone", this::showConeDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Cylinder", this::showCylinderDialog));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createShapeButton("Torus", this::showTorusDialog));

        return panel;
    }

    /**
     * Creates a button that opens the input workflow for a shape.
     *
     * @param text button text
     * @param action action performed when selected
     * @return configured shape button
     */
    private JButton createShapeButton(
            String text,
            Runnable action) {

        JButton button = new JButton(text);
        button.setFont(OPTION_FONT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(160, 38));
        button.setMaximumSize(new Dimension(160, 38));
        button.addActionListener(e -> action.run());

        return button;
    }

    /**
     * Displays the input dialog for a circle.
     */
    private void showCircleDialog() {
        Double radius = requestSingleValue(
                "Area of a Circle",
                "Radius:");

        if (radius == null) {
            return;
        }

        try {
            Circle circle = new Circle(radius);

            showResult(
                    "Area of a Circle",
                    "The area of the circle is",
                    circle.getArea(),
                    "square units",
                    circle);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a rectangle.
     */
    private void showRectangleDialog() {
        double[] values = requestTwoValues(
                "Area of a Rectangle",
                "Length:",
                "Width:");

        if (values == null) {
            return;
        }

        try {
            Rectangle rectangle =
                    new Rectangle(values[0], values[1]);

            showResult(
                    "Area of a Rectangle",
                    "The area of the rectangle is",
                    rectangle.getArea(),
                    "square units",
                    rectangle);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a square.
     */
    private void showSquareDialog() {
        Double side = requestSingleValue(
                "Area of a Square",
                "Side:");

        if (side == null) {
            return;
        }

        try {
            Square square = new Square(side);

            showResult(
                    "Area of a Square",
                    "The area of the square is",
                    square.getArea(),
                    "square units",
                    square);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the triangle construction menu.
     */
    private void showTriangleMenu() {
        String[] options = {
                "Base and Height",
                "Three Sides",
                "Cancel"
        };

        int selection = JOptionPane.showOptionDialog(
                mainFrame,
                "How would you like to define the triangle?",
                "Triangle",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (selection == 0) {
            showBaseHeightTriangleDialog();
        } else if (selection == 1) {
            showThreeSideTriangleDialog();
        }
    }

    /**
     * Creates a triangle from a base and height and displays its area.
     */
    private void showBaseHeightTriangleDialog() {
        double[] values = requestTwoValues(
                "Area of a Triangle",
                "Base:",
                "Height:");

        if (values == null) {
            return;
        }

        try {
            Triangle triangle =
                    new Triangle(values[0], values[1]);

            showResult(
                    "Area of a Triangle",
                    "The area of the triangle is",
                    triangle.getArea(),
                    "square units",
                    triangle);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Creates a triangle from three side lengths and displays its area and
     * classification.
     */
    private void showThreeSideTriangleDialog() {
        double[] values = requestThreeValues();

        if (values == null) {
            return;
        }

        try {
            Triangle triangle =
                    new Triangle(
                            values[0],
                            values[1],
                            values[2]);
            new ShapeDisplay(triangle);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a sphere.
     */
    private void showSphereDialog() {
        Double radius = requestSingleValue(
                "Volume of a Sphere",
                "Radius:");

        if (radius == null) {
            return;
        }

        try {
            Sphere sphere = new Sphere(radius);

            showResult(
                    "Volume of a Sphere",
                    "The volume of the sphere is",
                    sphere.getVolume(),
                    "cubic units",
                    sphere);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a cube.
     */
    private void showCubeDialog() {
        Double side = requestSingleValue(
                "Volume of a Cube",
                "Side:");

        if (side == null) {
            return;
        }

        try {
            Cube cube = new Cube(side);

            showResult(
                    "Volume of a Cube",
                    "The volume of the cube is",
                    cube.getVolume(),
                    "cubic units",
                    cube);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a cone.
     */
    private void showConeDialog() {
        double[] values = requestTwoValues(
                "Volume of a Cone",
                "Radius:",
                "Height:");

        if (values == null) {
            return;
        }

        try {
            Cone cone =
                    new Cone(values[0], values[1]);

            showResult(
                    "Volume of a Cone",
                    "The volume of the cone is",
                    cone.getVolume(),
                    "cubic units",
                    cone);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a cylinder.
     */
    private void showCylinderDialog() {
        double[] values = requestTwoValues(
                "Volume of a Cylinder",
                "Radius:",
                "Height:");

        if (values == null) {
            return;
        }

        try {
            Cylinder cylinder =
                    new Cylinder(values[0], values[1]);

            showResult(
                    "Volume of a Cylinder",
                    "The volume of the cylinder is",
                    cylinder.getVolume(),
                    "cubic units",
                    cylinder);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Displays the input dialog for a torus.
     */
    private void showTorusDialog() {
        double[] values = requestTwoValues(
                "Volume of a Torus",
                "Major Radius:",
                "Minor Radius:");

        if (values == null) {
            return;
        }

        try {
            Torus torus =
                    new Torus(values[0], values[1]);

            showResult(
                    "Volume of a Torus",
                    "The volume of the torus is",
                    torus.getVolume(),
                    "cubic units",
                    torus);

        } catch (IllegalArgumentException e) {
            showInputError(e.getMessage());
        }
    }

    /**
     * Requests a single numeric value from the user.
     *
     * @param title dialog title
     * @param label field label
     * @return entered value, or {@code null} when cancelled
     */
    private Double requestSingleValue(
            String title,
            String label) {

        JTextField field = createTextField();

        JPanel panel = createInputPanel(
                new String[]{label},
                new JTextField[]{field});

        SwingUtilities.invokeLater(field::requestFocusInWindow);

        if (isInputCancelled(panel, title)) {
            return null;
        }

        return parseDouble(field.getText(), label);
    }

    /**
     * Requests two numeric values from the user.
     *
     * @param title dialog title
     * @param firstLabel first field label
     * @param secondLabel second field label
     * @return entered values, or {@code null} when cancelled or invalid
     */
    private double[] requestTwoValues(
            String title,
            String firstLabel,
            String secondLabel) {

        JTextField firstField = createTextField();
        JTextField secondField = createTextField();

        JPanel panel = createInputPanel(
                new String[]{firstLabel, secondLabel},
                new JTextField[]{firstField, secondField});

        SwingUtilities.invokeLater(firstField::requestFocusInWindow);

        if (isInputCancelled(panel, title)) {
            return null;
        }

        Double first =
                parseDouble(firstField.getText(), firstLabel);

        if (first == null) {
            return null;
        }

        Double second =
                parseDouble(secondField.getText(), secondLabel);

        if (second == null) {
            return null;
        }

        return new double[]{first, second};
    }

    /**
     * Requests the three side lengths used to construct a triangle.
     *
     * @return entered side lengths, or {@code null} when cancelled or invalid
     */
    private double[] requestThreeValues() {

        String firstLabel = "Side A:";
        String secondLabel = "Side B:";
        String thirdLabel = "Side C:";

        JTextField firstField = createTextField();
        JTextField secondField = createTextField();
        JTextField thirdField = createTextField();

        JPanel panel = createInputPanel(
                new String[]{
                        firstLabel,
                        secondLabel,
                        thirdLabel
                },
                new JTextField[]{
                        firstField,
                        secondField,
                        thirdField
                });

        SwingUtilities.invokeLater(firstField::requestFocusInWindow);

        if (isInputCancelled(panel, "Triangle")) {
            return null;
        }

        Double first = parseDouble(
                firstField.getText(),
                firstLabel);

        if (first == null) {
            return null;
        }

        Double second = parseDouble(
                secondField.getText(),
                secondLabel);

        if (second == null) {
            return null;
        }

        Double third = parseDouble(
                thirdField.getText(),
                thirdLabel);

        if (third == null) {
            return null;
        }

        return new double[]{first, second, third};
    }

    /**
     * Creates a text field used for numeric shape dimensions.
     *
     * @return configured text field
     */
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(FIELD_SIZE);
        return field;
    }

    /**
     * Creates a reusable panel containing labeled input fields.
     *
     * @param labels field labels
     * @param fields corresponding text fields
     * @return configured input panel
     */
    private JPanel createInputPanel(
            String[] labels,
            JTextField[] fields) {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints =
                new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < labels.length; i++) {
            constraints.gridx = 0;
            constraints.gridy = i;

            panel.add(
                    new JLabel(labels[i]),
                    constraints);

            constraints.gridx = 1;

            panel.add(
                    fields[i],
                    constraints);
        }

        return panel;
    }

    /**
     * Displays an input panel and determines whether the user cancelled it.
     *
     * @param panel input panel
     * @param title dialog title
     * @return {@code true} when the user cancels or closes the dialog
     */
    private boolean isInputCancelled(
            JPanel panel,
            String title) {

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return result != JOptionPane.OK_OPTION;
    }

    /**
     * Converts text input to a finite numeric value.
     *
     * @param text text entered by the user
     * @param fieldName field being parsed
     * @return parsed value, or {@code null} when invalid
     */
    private Double parseDouble(
            String text,
            String fieldName) {

        if (text == null || text.trim().isEmpty()) {
            showInputError(
                    fieldName + " is required.");
            return null;
        }

        try {
            double value =
                    Double.parseDouble(text.trim());

            if (!Double.isFinite(value)) {
                showInputError(
                        fieldName
                                + " must be a finite number.");
                return null;
            }

            return value;

        } catch (NumberFormatException e) {
            showInputError(
                    fieldName
                            + " must be a valid number.");
            return null;
        }
    }

    /**
     * Displays an area or volume result and then opens a dimension-aware
     * visualization of the corresponding shape.
     *
     * @param title result dialog title
     * @param description result description
     * @param value calculated result
     * @param units result units
     * @param shape shape to visualize
     */
    private void showResult(
            String title,
            String description,
            double value,
            String units,
            Shape shape) {

        new ShapeDisplay(shape);
    }

    /**
     * Displays an input-validation error.
     *
     * @param message validation message
     */
    private void showInputError(String message) {
        JOptionPane.showMessageDialog(
                mainFrame,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Confirms whether the user wants to exit the application.
     */
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(
                mainFrame,
                "Are you sure you want to exit Geometry Modeling?",
                "Exit Geometry Modeling",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            mainFrame.dispose();
        }
    }

    /**
     * Launches the Geometry Modeling application on the Swing Event
     * Dispatch Thread.
     *
     * @param args command-line arguments; not used by this application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShapeApp::new);
    }
}
