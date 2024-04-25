/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The shapeAppGUI method creates several GUIs to display the main menu,
 * a sub-menu and results via a JOptionPane. When an option is selected, a
 * confirmatory GUI is prompted. The results are obtained and displayed via
 * GUI by calling the method for the appropriately selected class.
 * An image of the object is also displayed. The menu GUI remains until the
 * selects the "exit" button.
 *
 */

//package CMSC_335_Project2;

import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// declares a public class
public class ShapeApp {

    private JFrame mainFrame, triangleFrame;
    private JPanel mainPanel, trianglePanel, twoDimensionalPanel,
            threeDimensionalPanel, exitPanel, panelRow, entryPanel,
            triangleButtonPanel, triangleExitPanel;
    private ButtonGroup buttonGroup;
    private JRadioButton radioButtonCircle, radioButtonRectangle,
            radioButtonSquare, radioButtonTriangle, radioButtonSphere,
            radioButtonCube, radioButtonCone, radioButtonCylinder,
            radioButtonTorus, radioButtonBaseHeight, radioButtonThreeSides;
    private JButton buttonExit, triangleExitButton;
    private JLabel introLabel, mainMenuLabel, triangleIntroLabel;
    private JTextField radiusField, lengthField, widthField, heightField,
            majorRadiusField, minorRadiusField;
    private Dimension buttonSize;
    private Font introFont, mainMenuFont, radioButtonFont, exitButtonFont,
            triangleIntroFont;
    private ActionListener buttonListener;
    private JOptionPane optionPane;
    private JDialog dialog;
    private AbstractAction action;
    private ZonedDateTime myDateTime;
    public static String imagePath, folderPath;
    private ImageDisplay display;


    // declares a public constructor with no parameters
    // calls the shapeAppGUI method
    public ShapeApp(){

        shapeAppGUI();

    } // end constructor

    // method that generates the main menu via GUI
    private void shapeAppGUI(){

        // Prompt the user for the folder path
        folderPath = JOptionPane.showInputDialog(mainFrame, "Enter the folder path: ");
        if (folderPath == null || folderPath.trim().isEmpty()) {

            JOptionPane.showMessageDialog(mainFrame, "Invalid folder path. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        } // end if statement

        // creates the main frame, panel and labels
        mainFrame = new JFrame("Java Object Oriented Shapes Program");
        mainFrame.setSize(700,350);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,
                BoxLayout.Y_AXIS));

        introLabel = new JLabel("",JLabel.CENTER );
        introFont = new Font("Arial", Font.BOLD, 22);
        introLabel.setText("***** Welcome to the Java Object Oriented "
                + "Shapes Program *****");
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        introLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 0, 0, 0));
        introLabel.setFont(introFont);

        mainMenuLabel = new JLabel("",JLabel.CENTER );
        mainMenuFont = new Font("Arial", Font.BOLD, 20);
        mainMenuLabel.setText("Make a Selection:");
        mainMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuLabel.setFont(mainMenuFont);

        buttonGroup = new ButtonGroup();

        radioButtonFont = new Font("Arial", Font.PLAIN, 18);

        // create the panel for the two dimensional buttons
        twoDimensionalPanel = new JPanel();
        twoDimensionalPanel.setLayout(new BoxLayout(
                twoDimensionalPanel, BoxLayout.Y_AXIS));
        radioButtonCircle = new JRadioButton("Construct a Circle");
        radioButtonCircle.setFont(radioButtonFont);
        buttonGroup.add(radioButtonCircle);
        radioButtonRectangle = new JRadioButton("Construct a Rectangle ");
        radioButtonRectangle.setFont(radioButtonFont);
        buttonGroup.add(radioButtonRectangle);
        radioButtonSquare = new JRadioButton("Construct a Square");
        radioButtonSquare.setFont(radioButtonFont);
        buttonGroup.add(radioButtonSquare);
        radioButtonTriangle = new JRadioButton("Construct a Triangle ");
        radioButtonTriangle.setFont(radioButtonFont);
        buttonGroup.add(radioButtonTriangle);
        twoDimensionalPanel.add(radioButtonCircle);
        twoDimensionalPanel.add(radioButtonRectangle);
        twoDimensionalPanel.add(radioButtonSquare);
        twoDimensionalPanel.add(radioButtonTriangle);
        twoDimensionalPanel.add(Box.createHorizontalStrut(10));

        // create the panel for the three dimensional buttons
        threeDimensionalPanel = new JPanel();
        threeDimensionalPanel.setLayout(new BoxLayout(
                threeDimensionalPanel, BoxLayout.Y_AXIS));
        radioButtonSphere = new JRadioButton("Construct a Sphere ");
        radioButtonSphere.setFont(radioButtonFont);
        buttonGroup.add(radioButtonSphere);
        radioButtonCube = new JRadioButton("Construct a Cube ");
        radioButtonCube.setFont(radioButtonFont);
        buttonGroup.add(radioButtonCube);
        radioButtonCone = new JRadioButton("Construct a Cone");
        radioButtonCone.setFont(radioButtonFont);
        buttonGroup.add(radioButtonCone);
        radioButtonCylinder = new JRadioButton("Construct a Cylinder");
        radioButtonCylinder.setFont(radioButtonFont);
        buttonGroup.add(radioButtonCylinder);
        radioButtonTorus = new JRadioButton("Construct a Torus");
        radioButtonTorus.setFont(radioButtonFont);
        buttonGroup.add(radioButtonTorus);
        threeDimensionalPanel.add(radioButtonSphere);
        threeDimensionalPanel.add(radioButtonCube);
        threeDimensionalPanel.add(radioButtonCone);
        threeDimensionalPanel.add(radioButtonCylinder);
        threeDimensionalPanel.add(radioButtonTorus);

        // create the panel for the exit button
        exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel,
                BoxLayout.X_AXIS));
        buttonExit = new JButton("Exit");
        exitButtonFont = new Font("Arial", Font.BOLD, 20);
        buttonExit.setFont(exitButtonFont);
        exitPanel.add(buttonExit);

        buttonExit.setOpaque(true);
        buttonExit.setBackground(Color.RED);

        buttonSize = new Dimension(300, 50);
        buttonExit.setPreferredSize(buttonSize);
        buttonExit.setMaximumSize(buttonSize);

        // Set preferred sizes
        twoDimensionalPanel.setPreferredSize(new Dimension(
                240,
                twoDimensionalPanel.getPreferredSize().height));
        threeDimensionalPanel.setPreferredSize(new Dimension(
                240,
                threeDimensionalPanel.getPreferredSize().height));
        exitPanel.setPreferredSize(new Dimension(500,
                exitPanel.getPreferredSize().height));


        // Add components to the mainPanel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(introLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(mainMenuLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Create a panel to hold twoDimensionalPanel and threeDimensionalPanel
        panelRow = new JPanel();
        panelRow.setLayout(new BoxLayout(panelRow, BoxLayout.X_AXIS));
        panelRow.add(twoDimensionalPanel);
        panelRow.add(Box.createHorizontalStrut(10));
        panelRow.add(threeDimensionalPanel);

        mainPanel.add(panelRow);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(exitPanel);
        mainPanel.add(Box.createVerticalGlue());


        // add panel to frame
        mainFrame.add(mainPanel);

        // makes frame visible
        mainFrame.setVisible(true);

        // instance of an anonymous inner class that extends AbstractAction
        action = new AbstractAction(){

            // overrides the actionPerformed method inherited from the
            // ActionListener interface. The subsequent lines get the source
            // of the event then performs the appropriate action
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == radioButtonCircle) {

                    radiusField = new JTextField();
                    radiusField.setPreferredSize(
                            new Dimension(200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    // Add label to entryPanel
                    entryPanel.add(new JLabel("Radius:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(0, 10, 0, 0);

                    // Add text field to entryPanel
                    entryPanel.add(radiusField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter the radius",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String radius = radiusField.getText();

                        if (!radius.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data: "
                                            + "\nRadius: " + radius +
                                            " units\n" + "Is this correct?",
                                    "Area of a Circle",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Circle circle = new Circle(
                                            Double.parseDouble(radius));
                                    String message = "The area of the "
                                            + "circle is\n" + String.format(
                                            "%.2f",
                                            circle.getArea()) +
                                            " squared units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Area of a Circle",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Circle.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Data is incorrect. "
                                                    + "\nTry again",
                                            "Area of a Circle",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "No data entered. "
                                                    + "\nTry again",
                                            "Area of a Circle",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "No data entered. \nTry again",
                                    "Area of a Circle",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(mainFrame,
                                "No data entered. \nTry again",
                                "Area of a Circle",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonRectangle) {

                    lengthField = new JTextField();
                    lengthField.setPreferredSize(
                            new Dimension(200, 25));
                    widthField = new JTextField();
                    widthField.setPreferredSize(
                            new Dimension(200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    // Length panel
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    entryPanel.add(new JLabel("Length:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    entryPanel.add(lengthField, gbc);

                    // Width panel
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    entryPanel.add(new JLabel("Width:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    entryPanel.add(widthField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter Length and Width",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String length = lengthField.getText();
                        String width = widthField.getText();


                        if (!length.isEmpty() && !width.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\n"
                                            + "Length: " + length + " units\n"
                                            + "Width: " + width
                                            + " units\nIs this correct?",
                                    "Area of a Rectangle",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Rectangle rectangle = new Rectangle(
                                            Double.parseDouble(length),
                                            Double.parseDouble(width));

                                    String message = "The area of the "
                                            + "rectangle is\n" +
                                            String.format("%.2f",
                                                    rectangle.getArea()) +
                                            " squared units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Area of a Rectangle",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Rectangle.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Data is incorrect. "
                                                    + "\nTry again",
                                            "Area of a Rectangle",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "No data entered. "
                                                    + "\nTry again",
                                            "Area of a Rectangle",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(mainFrame,
                                    "No data entered. \nTry again",
                                    "Area of a Rectangle",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(mainFrame,
                                "No data entered. \nTry again",
                                "Area of a Rectangle",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonSquare) {

                    lengthField = new JTextField();
                    lengthField.setPreferredSize(
                            new Dimension(200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    // Add label to entryPanel
                    entryPanel.add(new JLabel("Length:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(0, 10, 0, 0);

                    // Add text field to entryPanel
                    entryPanel.add(lengthField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter the length",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String length = lengthField.getText();

                        if (!length.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\n"
                                            + "Length: " + length +
                                            " units\nIs this correct?",
                                    "Area of a Square",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Square square = new Square(
                                            Double.parseDouble(length));
                                    String message = "The area of the square "
                                            + "is\n" + String.format(
                                            "%.2f",
                                            square.getArea()) +
                                            " squared units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Area of a Square",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Square.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Data is incorrect.\n"
                                                    + "Try again",
                                            "Area of a Square",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "No data entered. "
                                                    + "\nTry again",
                                            "Area of a Square",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(mainFrame,
                                    "No data entered. \nTry again",
                                    "Area of a Square",
                                    JOptionPane.WARNING_MESSAGE);

                        } // end if-else statements

                    } else {

                        JOptionPane.showMessageDialog(mainFrame,
                                "No data entered. \nTry again",
                                "Area of a Square",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonTriangle) {

                    triangleFrame = new JFrame("Triangle Menu");
                    triangleFrame.setSize(700,350);
                    triangleFrame.setLocationRelativeTo(null);
                    triangleFrame.setResizable(false);
                    triangleFrame.setDefaultCloseOperation(
                            JFrame.EXIT_ON_CLOSE);

                    trianglePanel = new JPanel();
                    trianglePanel.setLayout(new BoxLayout(trianglePanel,
                            BoxLayout.Y_AXIS));

                    triangleIntroLabel = new JLabel("",
                            JLabel.CENTER );
                    triangleIntroFont = new Font("Arial",
                            Font.BOLD, 22);

                    triangleIntroLabel.setText("Select the triangle"
                            + " data you will enter:");
                    triangleIntroLabel.setAlignmentX(
                            Component.CENTER_ALIGNMENT);
                    triangleIntroLabel.setBorder(
                            BorderFactory.createEmptyBorder(
                                    10, 0, 0, 0));
                    triangleIntroLabel.setFont(triangleIntroFont);

                    // create panel for buttons
                    triangleButtonPanel = new JPanel();
                    triangleButtonPanel.setLayout(new BoxLayout(
                            triangleButtonPanel, BoxLayout.Y_AXIS));
                    triangleButtonPanel.setAlignmentX(
                            Component.CENTER_ALIGNMENT);

                    radioButtonBaseHeight  = new JRadioButton(
                            "Base and Height");
                    radioButtonBaseHeight.setFont(radioButtonFont);
                    buttonGroup.add(radioButtonBaseHeight);


                    radioButtonThreeSides = new JRadioButton("Three Sides");
                    radioButtonThreeSides.setFont(radioButtonFont);
                    buttonGroup.add(radioButtonThreeSides);


                    triangleButtonPanel.add(radioButtonBaseHeight);
                    triangleButtonPanel.add(radioButtonThreeSides);

                    // create the panel for the exit button
                    triangleExitPanel = new JPanel();
                    triangleExitPanel.setLayout(new FlowLayout(
                            FlowLayout.CENTER));
                    triangleExitPanel.setAlignmentX(
                            Component.CENTER_ALIGNMENT);

                    // Create horizontal glue to center the exit button
                    triangleExitPanel.add(Box.createHorizontalGlue());

                    triangleExitButton = new JButton("Exit");
                    exitButtonFont = new Font(
                            "Arial", Font.BOLD, 20);
                    triangleExitButton.setFont(exitButtonFont);

                    triangleExitButton.setOpaque(true);
                    triangleExitButton.setBackground(Color.RED);
                    buttonSize = new Dimension(300, 50);
                    triangleExitButton.setPreferredSize(buttonSize);
                    triangleExitButton.setMaximumSize(buttonSize);
                    triangleExitPanel.add(triangleExitButton);

                    // Set preferred sizes
                    trianglePanel.setPreferredSize(new Dimension(
                            700,
                            trianglePanel.getPreferredSize().height));
                    triangleExitPanel.setPreferredSize(new Dimension(700,
                            triangleExitPanel.getPreferredSize().height));

                    // Add components to the mainPanel
                    trianglePanel.add(Box.createVerticalStrut(50));
                    trianglePanel.add(triangleIntroLabel);
                    trianglePanel.add(Box.createVerticalStrut(20));
                    trianglePanel.add(triangleButtonPanel);
                    trianglePanel.add(Box.createVerticalStrut(20));
                    trianglePanel.add(triangleExitPanel);

                    // add panel to frame
                    triangleFrame.add(trianglePanel);

                    // makes frame visible
                    triangleFrame.setVisible(true);

                    // instance of an anonymous inner class that
                    // extends AbstractAction
                    action = new AbstractAction(){

                        // overrides the actionPerformed method inherited from
                        // the ActionListener interface. The subsequent lines
                        // get the source of the event then performs the
                        // appropriate action
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            Triangle triangle = new Triangle();

                            if (e.getSource() == radioButtonBaseHeight) {

                                triangle.setSelection(1);

                                buttonGroup.clearSelection();

                            }else if (e.getSource() == radioButtonThreeSides) {

                                triangle.setSelection(2);

                                buttonGroup.clearSelection();

                            }else if (e.getSource() == triangleExitButton) {

                                triangleFrame.dispose();

                                buttonGroup.clearSelection();

                            } // end if-else statements

                            triangle.getArea();
                            triangle.determineTriangleType();

                        } // end actionPerformed method

                    }; // end anonymous inner class

                    // Add action listener
                    radioButtonBaseHeight.addActionListener(action);
                    radioButtonThreeSides.addActionListener(action);
                    triangleExitButton.addActionListener(action);

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonSphere) {

                    radiusField = new JTextField();
                    radiusField.setPreferredSize(new Dimension(
                            200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    // Add label to entryPanel
                    entryPanel.add(new JLabel("Radius:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(0, 10, 0, 0);

                    // Add text field to entryPanel
                    entryPanel.add(radiusField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter the radius",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String radius = radiusField.getText();

                        if (!radius.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\n"
                                            + "Radius: " + radius +
                                            " units\nIs this correct?",
                                    "Volume of a Sphere",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Sphere sphere = new Sphere(
                                            Double.parseDouble(radius));
                                    String message = "The volume of the sphere "
                                            + "is\n" + String.format(
                                            "%.2f",
                                            sphere.getVolume()) +
                                            " cubed units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Volume of a Sphere",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Sphere.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);

                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Data is incorrect. \nTry "
                                                    + "again", "Volume of "
                                                    + "a Sphere",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "No data entered. "
                                                    + "\nTry again",
                                            "Volume of a Sphere",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(mainFrame,
                                    "No data entered. \nTry again",
                                    "Volume of a Sphere",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(mainFrame,
                                "No data entered. \nTry again",
                                "Volume of a Sphere",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonCube) {

                    lengthField = new JTextField();
                    lengthField.setPreferredSize(new Dimension(
                            200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    // Add label to entryPanel
                    entryPanel.add(new JLabel("Length:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(0, 10, 0, 0);

                    // Add text field to entryPanel
                    entryPanel.add(lengthField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter the length",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String length = lengthField.getText();

                        if (!length.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\n"
                                            + "Length: " + length +
                                            " units\nIs this correct?",
                                    "Volume of a Cube",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Cube cube = new Cube(
                                            Double.parseDouble(length));
                                    String message = "The volume of the cube "
                                            + "is \n" + String.format(
                                            "%.2f",
                                            cube.getVolume()) +
                                            " cubed units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Volume of a Cube",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Cube.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame, "Data"
                                                    + " is incorrect. \nTry "
                                                    + "again", "Volume of"
                                                    + " a Cube",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame, "No "
                                                    + "data entered. \nTry "
                                                    + "again", "Volume of "
                                                    + "a Cube",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(
                                    mainFrame, "No data "
                                            + "entered. \nTry again",
                                    "Volume of a Cube",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(
                                mainFrame, "No data entered."
                                        + " \nTry again", "Volume of a "
                                        + "Cube",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonCone) {

                    radiusField = new JTextField();
                    radiusField.setPreferredSize(new Dimension(
                            200, 25));
                    heightField = new JTextField();
                    heightField.setPreferredSize(new Dimension(
                            200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    // Length panel
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    entryPanel.add(new JLabel("Radius:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    entryPanel.add(radiusField, gbc);

                    // Width panel
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    entryPanel.add(new JLabel("Height:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    entryPanel.add(heightField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter Radius and Height",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String radius = radiusField.getText();
                        String height = heightField.getText();


                        if (!radius.isEmpty() && !height.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\n"
                                            + "Radius: " + radius +
                                            " units\nHeight: " + height +
                                            " units\nIs this correct?",
                                    "Volume of a Cone",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.
                                            QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Cone cone = new Cone(
                                            Double.parseDouble(radius),
                                            Double.parseDouble(height));
                                    String message = "The volume of the cone "
                                            + "is \n" + String.format(
                                            "%.2f",
                                            cone.getVolume()) +
                                            " cubed units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Volume of a Cone",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Cone.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame, "Data "
                                                    + "is incorrect. \nTry "
                                                    + "again", "Volume "
                                                    + "of a Cone",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame, "No "
                                                    + "data entered. \nTry "
                                                    + "again", "Volume "
                                                    + "of a Cone",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(
                                    mainFrame, "No data "
                                            + "entered. \nTry again",
                                    "Volume of a Cone",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(mainFrame,
                                "No data entered. \nTry again",
                                "Volume of a Cone",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonCylinder) {

                    radiusField = new JTextField();
                    radiusField.setPreferredSize(new Dimension(
                            200, 25));
                    heightField = new JTextField();
                    heightField.setPreferredSize(new Dimension(
                            200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    // Length panel
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    entryPanel.add(new JLabel("Radius:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    entryPanel.add(radiusField, gbc);

                    // Width panel
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    entryPanel.add(new JLabel("Height:"), gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    entryPanel.add(heightField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter Radius and Height",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String radius = radiusField.getText();
                        String height = heightField.getText();


                        if (!radius.isEmpty() && !height.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\n"
                                            + "Radius: " + radius
                                            + " units\nHeight: "
                                            + height +
                                            " units\nIs this correct?",
                                    "Volume of a Cylinder",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.
                                            QUESTION_MESSAGE);

                            switch (result1) {
                                case JOptionPane.YES_OPTION -> {

                                    Cylinder cylinder = new Cylinder(
                                            Double.parseDouble(radius),
                                            Double.parseDouble(height));
                                    String message = "The volume of the "
                                            + "cylinder is \n" + String.format(
                                            "%.2f",
                                            cylinder.getVolume())
                                            + " cubed units.";
                                    JOptionPane.showMessageDialog(
                                            null, message,
                                            "Volume of a Cylinder",
                                            JOptionPane.
                                                    INFORMATION_MESSAGE);
                                    String imageName = "Cylinder.png";
                                    imagePath = folderPath + "/" + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }

                                case JOptionPane.NO_OPTION -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Data is incorrect. \nTry "
                                                    + "again", "Volume "
                                                    + "of a Cylinder",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                                default -> {

                                    JOptionPane.showMessageDialog(
                                            mainFrame, "No "
                                                    + "data entered. \nTry "
                                                    + "again",
                                            "Volume of a Cylinder",
                                            JOptionPane.
                                                    WARNING_MESSAGE);
                                    break;

                                }

                            } // end switch statements

                        } else {

                            JOptionPane.showMessageDialog(
                                    mainFrame, "No data "
                                            + "entered. \nTry again",
                                    "Volume of a Cylinder",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(
                                mainFrame, "No data entered."
                                        + "\nTry again", "Volume of a "
                                        + "Cylinder",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == radioButtonTorus) {

                    minorRadiusField = new JTextField();
                    minorRadiusField.setPreferredSize(new Dimension(
                            200, 25));
                    majorRadiusField = new JTextField();
                    majorRadiusField.setPreferredSize(new Dimension(
                            200, 25));

                    entryPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    // Length panel
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    entryPanel.add(new JLabel("Minor Radius:"),
                            gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    entryPanel.add(minorRadiusField, gbc);

                    // Width panel
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    entryPanel.add(new JLabel("Major Radius:"),
                            gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    entryPanel.add(majorRadiusField, gbc);

                    int result = JOptionPane.showOptionDialog(null,
                            entryPanel, "Enter major radius and minor"
                                    + " radius",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, null, null);

                    if (result == JOptionPane.OK_OPTION) {

                        String majorRadius = majorRadiusField.getText();
                        String minorRadius = minorRadiusField.getText();


                        if (!majorRadius.isEmpty() && !minorRadius.isEmpty()) {

                            int result1 = JOptionPane.showConfirmDialog(
                                    mainFrame, "You entered the "
                                            + "following data:\nMajor"
                                            + " Radius: " + majorRadius +
                                            " units\nMinor Radius: "
                                            + minorRadius + " units\nIs "
                                            + "this correct?",
                                    "Volume of a Torus",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (Double.parseDouble(majorRadius) <
                                    Double.parseDouble(minorRadius)) {

                                JOptionPane.showMessageDialog(
                                        mainFrame,
                                        "Error! \nThe Major radius must be"
                                                + " larger than the minor "
                                                + "radius. \nTry again.",
                                        "Warning",
                                        JOptionPane.WARNING_MESSAGE);


                            } else {

                                switch (result1) {
                                    case JOptionPane.YES_OPTION -> {

                                        Torus torus = new Torus(
                                                Double.parseDouble(
                                                        majorRadius),
                                                Double.parseDouble(
                                                        minorRadius));
                                        String message = "The volume of the "
                                                + "torus is\n" + String.format(
                                                "%.2f",
                                                torus.getVolume())
                                                + " cubed units.";
                                        JOptionPane.showMessageDialog(
                                                null, message,
                                                "Volume of a Torus",
                                                JOptionPane.
                                                        INFORMATION_MESSAGE);
                                        String imageName = "Torus.png";
                                        imagePath = folderPath + "/" + imageName;
                                        display = new ImageDisplay(imagePath);
                                        break;

                                    }

                                    case JOptionPane.NO_OPTION -> {

                                        JOptionPane.showMessageDialog(
                                                mainFrame,
                                                "Data is incorrect. \nTry "
                                                        + "again", "Volume "
                                                        + "of a Torus",
                                                JOptionPane.
                                                        WARNING_MESSAGE
                                        );
                                        break;

                                    }

                                    default -> {

                                        JOptionPane.showMessageDialog(
                                                mainFrame,
                                                "No data entered. \nTry "
                                                        + "again", "Volume "
                                                        + "of a Torus",
                                                JOptionPane.
                                                        WARNING_MESSAGE
                                        );
                                        break;

                                    }

                                } // end switch statements

                            } // end if-else statements

                        } else {

                            JOptionPane.showMessageDialog(mainFrame,
                                    "No data entered. \nTry again",
                                    "Volume of a Torus",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } else {

                        JOptionPane.showMessageDialog(mainFrame,
                                "No data entered. \nTry again",
                                "Volume of a Torus",
                                JOptionPane.WARNING_MESSAGE);

                    } // end if-else statements

                    buttonGroup.clearSelection();

                } else if (e.getSource() == buttonExit) {

                    int result = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "<html><center><font face='Arial' "
                                    + "size='5'><b>Are you sure you want to "
                                    + "exit?<b><font><center></html>",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {


                        // creates an object of ZonedDateTime
                        myDateTime = ZonedDateTime.now();

                        // created two separate formats of date and time
                        DateTimeFormatter myDateFormat =
                                DateTimeFormatter.
                                        ofPattern("EEEE MMM dd, yyyy");
                        DateTimeFormatter myTimeFormat =
                                DateTimeFormatter.
                                        ofPattern("h:mm a "
                                                + "(HH:mm 'hrs') zzzz");

                        // applies the created formats to separate
                        //date and time strings
                        String dateText = myDateTime.format(
                                myDateFormat);
                        String timeText = myDateTime.format(
                                myTimeFormat);

                        // message for JOptionPane
                        String exitMessage = "<html><center><font face='Arial'"
                                + "size='5'><b>Thank you for using the <br/>"
                                + "Java Object Oriented Shapes Program.<br/>"
                                + "<br/> Today is " + dateText + "<br/>" +
                                timeText + "<br/><br/>Goodbye!!!</b></font>"
                                + "</center></html>";


                        // creates an instance of JOptionPane
                        optionPane = new JOptionPane(exitMessage,
                                JOptionPane.INFORMATION_MESSAGE,
                                JOptionPane.DEFAULT_OPTION,
                                null, new Object[]{}, null);

                        // creates a new JDialog object
                        dialog = new JDialog();
                        dialog.setSize(400,300);
                        dialog.setLocationRelativeTo(null);
                        dialog.setTitle("Message");

                        // blocks user interaction with other windows until
                        // it is closed.
                        dialog.setModal(true);

                        dialog.setContentPane(optionPane);

                        dialog.setDefaultCloseOperation(
                                JDialog.DO_NOTHING_ON_CLOSE);
                        dialog.pack();


                        // creates a new timer object to exit the program,
                        // with a 4000 millisecond (4 second)delay
                        Timer timer = new Timer(4000,
                                new AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent ae) {
                                        System.exit(0);

                                    } // end actionPerformed

                                }); // end Timer object

                        // fires only once and does not repeat;
                        // used to introduce a delay
                        timer.setRepeats(false);

                        //start timer to close JDialog
                        timer.start();

                        // makes the dialog box visible
                        dialog.setVisible(true);

                    } // end if statement

                } // end if-else statements

            }; // end actionPerformed method

        }; // end anonymous inner class

        // assigns the action object (instance of AbstractAction) to
        // the buttonListener variable
        buttonListener = action;

        // adds the buttonListener as an ActionListener to each button
        radioButtonCircle.addActionListener(buttonListener);
        radioButtonRectangle.addActionListener(buttonListener);
        radioButtonSquare.addActionListener(buttonListener);
        radioButtonTriangle.addActionListener(buttonListener);
        radioButtonSphere.addActionListener(buttonListener);
        radioButtonCube.addActionListener(buttonListener);
        radioButtonCone.addActionListener(buttonListener);
        radioButtonCylinder.addActionListener(buttonListener);
        radioButtonTorus.addActionListener(buttonListener);
        buttonExit.addActionListener(buttonListener);

    } // end shapeAppGUI method


    // main method executes the program
    public static void main(String[] args) {

        // creates an instance of the ShapeApp class, executing it
        ShapeApp shapeApp = new ShapeApp();

    } // end main

} // end ShapApp class


