/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The "Triangle" class is a subclass of the "TwoDimensional" class. It
 * represents a triangle in a two-dimensional space. The class includes
 * several instance variables. The program provides a constructor that takes
 * the selection as a parameter and assigns it to the "selection" instance
 * variable. The "Triangle" class includes methods to retrieve several
 * parameters. The class overrides the abstract method "getArea()" inherited
 * from the "TwoDimensional" class. The overridden method generates a GUI menu
 * and dialog boxes which prompt the user for input depending on the selection
 * value. If selection equals 1, the user is asked for the base and height of
 * the triangle, and the area is calculated as (base * height) / 2. If
 * selection equals 2, the user is prompted to enter the side lengths of the
 * triangle and checks if the triangle inequality is satisfied. If valid side
 * lengths are entered, the area is calculated using Heron's formula.
 * Otherwise, an error message is displayed. The class also includes a private
 * method "getInputDouble()" that handles user input validation for double
 * values. The class includes a method "determineTriangleType()" that
 * determines the type of triangle based on the selection and side lengths. It
 * calculates the angles of the triangle, checks if the sides satisfy the
 * triangle inequality, and identifies the triangle type based on the angles
 * and side lengths. The results are displayed via a JOptionPane.
 *
 */

//package CMSC_335_Project2;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// declares a public class that extends its parent class
public class Triangle extends TwoDimensional {

    private double area_triangle, side_a, side_b, side_c;
    private int selection, angle_a, angle_b, angle_c, triType;
    private String triangle_type, base, height,imagePath, folderPath;
    private final boolean side_validation = false;
    private JPanel entryPanel;
    private JTextField baseField, heightField,
            sideAField, sideBField, sideCField;
    private ImageDisplay display;

    // declares a public constructor with no parameters
    public Triangle() {

    } // end constructor

    public void setSelection(int selection) {

        this.selection = selection;
    }

    // declares a public method; retrieves and returns the value stored
    // in the " selection"
    public int getSelection() {

        return selection;

    }// end getRadius method

    // declares a public method; retrieves and returns the value stored
    // in the "triangle_type"
    public String getTriangleType() {

        return triangle_type;

    }// end getRadius method

    // declares a public method; retrieves and returns the value stored
    // in the "side_a"
    public double getSideA() {

        return side_a;

    }// end getSideA method

    // declares a public method; retrieves and returns the value stored
    // in the "side_b"
    public double getSideB() {

        return side_b;

    }// end getSideB method

    // declares a public method; retrieves and returns the value stored
    // in the "side_c"
    public double getSideC() {

        return side_c;

    }// end getSideC method

    // declares a public method; retrieves and returns the value stored
    // in the "side_validation"
    public boolean getSideValidation() {

        return side_validation;

    }// end getSideValidation method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getArea() {

        double semi_perimeter;

        folderPath = ShapeApp.folderPath;

        if (selection == 1) {

            baseField = new JTextField();
            baseField.setPreferredSize(new Dimension(200, 25));
            heightField = new JTextField();
            heightField.setPreferredSize(new Dimension(200, 25));

            entryPanel = new JPanel(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Base panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            entryPanel.add(new JLabel("Base:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            entryPanel.add(baseField, gbc);

            // Height panel
            gbc.gridx = 0;
            gbc.gridy = 1;
            entryPanel.add(new JLabel("Height:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            entryPanel.add(heightField, gbc);

            int result = JOptionPane.showOptionDialog(
                    null, entryPanel,
                    "Enter Base and Height",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            if (result == JOptionPane.OK_OPTION) {

                base = baseField.getText();
                height = heightField.getText();


                if (!base.isEmpty() && !height.isEmpty()) {

                    int result1 = JOptionPane.showConfirmDialog(
                            null, "You entered the "
                                    + "following data:\nBase: " +
                                    base + " units\nHeight: " + height +
                                    " units \nIs this correct?",
                            "Area of a triangle",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    switch (result1) {
                        case JOptionPane.YES_OPTION -> {

                            area_triangle = (Double.parseDouble(base) *
                                    Double.parseDouble(height)) / 2;

                            String message = "The area of the triangle is \n" +
                                    String.format("%.2f",
                                            area_triangle) +
                                    " squared units. " +
                                    determineTriangleType();
                            JOptionPane.showMessageDialog(null,
                                    message, "Area of a Triangle",
                                    JOptionPane.INFORMATION_MESSAGE);
                            String imageName = "Triangle.png";
                            imagePath = folderPath + "/" + imageName;
                            display = new ImageDisplay(imagePath);
                            break;
                        }

                        case JOptionPane.NO_OPTION -> {

                            JOptionPane.showMessageDialog(null,
                                    "Data is incorrect. \nTry again",
                                    "Area of a Triangle",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        }

                        default -> {

                            JOptionPane.showMessageDialog(null,
                                    "No data entered. \nTry again",
                                    "Area of a Triangle",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        }

                    } // end switch statements

                } else {

                    JOptionPane.showMessageDialog(null,
                            "No data entered. \nTry again",
                            "Area of a Triangle",
                            JOptionPane.WARNING_MESSAGE);

                }

            } else {

                JOptionPane.showMessageDialog(null,
                        "No data entered. \nTry again",
                        "Area of a Triangle",
                        JOptionPane.WARNING_MESSAGE);

            } // end if-else statements

            return area_triangle;

        } else if (selection == 2) {

            sideAField = new JTextField();
            sideAField.setPreferredSize(new Dimension(200, 25));
            sideBField = new JTextField();
            sideBField.setPreferredSize(new Dimension(200, 25));
            sideCField = new JTextField();
            sideCField.setPreferredSize(new Dimension(200, 25));

            entryPanel = new JPanel(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Side A
            gbc.gridx = 0;
            gbc.gridy = 0;
            entryPanel.add(new JLabel("Side A:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            entryPanel.add(sideAField, gbc);

            // Side B
            gbc.gridx = 0;
            gbc.gridy = 1;
            entryPanel.add(new JLabel("Side B:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            entryPanel.add(sideBField, gbc);

            // Side C panel
            gbc.gridx = 0;
            gbc.gridy = 2;
            entryPanel.add(new JLabel("Side C:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            entryPanel.add(sideCField, gbc);

            int result = JOptionPane.showOptionDialog(null,
                    entryPanel, "Enter Side A, Side B and Side C",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            if (result == JOptionPane.OK_OPTION) {

                side_a = Double.parseDouble(sideAField.getText());
                side_b = Double.parseDouble(sideBField.getText());
                side_c = Double.parseDouble(sideCField.getText());

                String message, message1, imageName;

                if (!sideAField.getText().isEmpty() &&
                        !sideAField.getText().isEmpty() &&
                        !sideAField.getText().isEmpty()) {

                    int result1 = JOptionPane.showConfirmDialog(
                            null, "You entered the "
                                    + "following data:\nLength of "
                                    + "side A: " + side_a + " units\nLength of "
                                    + "side B: " + side_b +
                                    " units\nLength " + "of side C: " +
                                    side_c + " units. \nIs this correct?",
                            "Area of a Triangle",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    switch (result1) {

                        case JOptionPane.YES_OPTION -> {

                            semi_perimeter = side_a + side_b + side_c;
                            area_triangle = (Math.sqrt(semi_perimeter *
                                    (semi_perimeter - side_a) *
                                    (semi_perimeter - side_b) *
                                    (semi_perimeter - side_c)));

                            message = determineTriangleType();

                            switch (triType) {

                                case 1 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName = "Equilateral.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }
                                case 2 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName =
                                            "Acute_Isosceles.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }
                                case 3 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName =
                                            "Obtuse_Isosceles.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }
                                case 4 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName =
                                            "Right_Isosceles.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                }
                                case 5 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName = "Acute_Scalene.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }
                                case 6 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName = "Obtuse_Scalene.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }
                                case 7 -> {

                                    message1 = "\nThe area of the "
                                            + "triangle is \n"
                                            + String.format("%.2f",
                                            area_triangle) + " squared "
                                            + "units. ";

                                    imageName = "Right_Scalene.png";
                                    imagePath = folderPath + "/"
                                            + imageName;
                                    display = new ImageDisplay(imagePath);
                                    break;

                                }
                                default -> {

                                    message1 = "";

                                    break;

                                }
                            } // end switch statement

                            JOptionPane.showMessageDialog(null,
                                    (message1 + message), "Area of a Triangle",
                                    JOptionPane.INFORMATION_MESSAGE);

                            break;

                        }

                        case JOptionPane.NO_OPTION -> {

                            JOptionPane.showMessageDialog(null,
                                    "Data is incorrect. \nTry again",
                                    "Area of a Triangle",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        }

                        default -> {

                            JOptionPane.showMessageDialog(null,
                                    "No data entered. \nTry again",
                                    "Area of a Triangle",
                                    JOptionPane.WARNING_MESSAGE);
                            break;

                        }

                    } // end switch statements

                } else {

                    JOptionPane.showMessageDialog(null,
                            "No data entered. \nTry again",
                            "Area of a Triangle",
                            JOptionPane.WARNING_MESSAGE);

                }

            } else {

                JOptionPane.showMessageDialog(null,
                        "No data entered. Try again",
                        "Area of a Triangle",
                        JOptionPane.WARNING_MESSAGE);

            } // end if-else statements

        }// end if-else statements

        return area_triangle;

    } // end getArea method


    // method that calculates the angles based on side length and determines
    // the type of triangle
    public String determineTriangleType() {

        if (selection == 2) {

            angle_c = (int) (Math.round(Math.toDegrees(Math.acos(
                    (side_b * side_b + side_c * side_c - side_a * side_a)
                            / (2 * side_b * side_c)))));
            angle_a = (int) (Math.round(Math.toDegrees(Math.acos(
                    (side_a * side_a + side_c * side_c - side_b * side_b)
                            / (2 * side_a * side_c)))));
            angle_b = (int) Math.round(180 - angle_a - angle_c);

            String message = "\nThe calculated angles of the triangle "
                    + "are as follows:\n"
                    + "Angle A: " + angle_a + " degrees\n"
                    + "Angle B: " + angle_b + " degrees\n"
                    + "Angle C: " + angle_c + " degrees.";

            // Check if sides satisfy the triangle inequality
            if (side_a + side_b <= side_c || side_b + side_c <= side_a
                    || side_c + side_a <= side_b) {

                triangle_type = "\nThe given side lengths do not form "
                        + "a valid triangle.";

            } else if ((angle_a == 60 && angle_b == 60 && angle_c == 60)
                    && (side_a == side_b && side_b == side_c)){

                triangle_type = message + "\nThe type of triangle is an "
                        + "Equilateral Triangle.";
                triType = 1;

            } else if ((angle_a < 90 && angle_b < 90 && angle_c < 90) &&
                    (side_a == side_b || side_a == side_c
                            || side_b == side_c)){

                triangle_type = message + "\nThe type of triangle is Acute "
                        + "Isosceles Triangle";
                triType = 2;

            } else if ((angle_a > 90 || angle_b > 90 || angle_c > 90) &&
                    (side_a == side_b || side_a == side_c
                            || side_b == side_c)){

                triangle_type = message + "\nThe type of triangle is Obtuse "
                        + "Isosceles Triangle";
                triType = 3;

            } else if ((angle_a == 90 || angle_b == 90 || angle_c == 90) &&
                    (side_a == side_b || side_a == side_c || side_b == side_c)
                    && (angle_a < 90 || angle_b < 90 || angle_c < 90)){

                triangle_type = message + "\nThe type of triangle is Right "
                        + "Isosceles Triangle";
                triType = 4;

            } else if ((angle_a < 90 && angle_b < 90 && angle_c < 90) &&
                    (side_a != side_b && side_a != side_c
                            && side_b != side_c)){

                triangle_type = message + "\nThe type of triangle is Acute "
                        + "Scalene Triangle";
                triType = 5;

            } else if ((angle_a > 90 || angle_b > 90 || angle_c > 90) &&
                    (side_a != side_b && side_a != side_c
                            && side_b != side_c)){

                triangle_type = message + "\nThe type of triangle is Obtuse "
                        + "Scalene Triangle";
                triType = 6;

            } else if ((angle_a == 90 || angle_b == 90 || angle_c == 90)
                    && (side_a != side_b && side_a != side_c
                    && side_b != side_c)){

                triangle_type = message + "\nThe type of triangle is Right "
                        + "Scalene Triangle";
                triType = 7;

            } // end if-else statements

        } else if (selection == 1) {

            triangle_type = "\nGiven only the height and weight, the program "
                    + "is unable to determine the type of triangle.";

        } // end if-else statements

        return triangle_type;

    } // end determineTriangleType method

} // end Triangle class



