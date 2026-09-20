package ui.workflow;

import geometry.twodimensional.Circle;
import geometry.twodimensional.Rectangle;
import geometry.twodimensional.Square;
import geometry.twodimensional.Triangle;
import ui.ShapeDisplay;
import ui.input.ShapeInputDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Coordinates user-input workflows for two-dimensional shapes.
 */
public class TwoDimensionalShapeWorkflow {

    private final Component parent;
    private final ShapeInputDialog inputDialog;

    /**
     * Creates the two-dimensional shape workflow.
     *
     * @param parent parent component for dialogs
     */
    public TwoDimensionalShapeWorkflow(Component parent) {
        this.parent = parent;
        this.inputDialog =
                new ShapeInputDialog(parent);
    }

    /**
     * Requests a radius and displays a circle.
     */
    public void showCircleDialog() {
        Double radius =
                inputDialog.requestSingleValue(
                        "Area of a Circle",
                        "Radius:");

        if (radius == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Circle(radius));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests dimensions and displays a rectangle.
     */
    public void showRectangleDialog() {
        double[] values =
                inputDialog.requestTwoValues(
                        "Area of a Rectangle",
                        "Length:",
                        "Width:");

        if (values == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Rectangle(
                            values[0],
                            values[1]));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests a side length and displays a square.
     */
    public void showSquareDialog() {
        Double side =
                inputDialog.requestSingleValue(
                        "Area of a Square",
                        "Side:");

        if (side == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Square(side));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Allows the user to choose how a triangle will be defined.
     */
    public void showTriangleMenu() {
        String[] options = {
                "Base and Height",
                "Three Sides",
                "Cancel"
        };

        int selection =
                JOptionPane.showOptionDialog(
                        parent,
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
     * Requests a base and height and displays a triangle.
     */
    private void showBaseHeightTriangleDialog() {
        double[] values =
                inputDialog.requestTwoValues(
                        "Area of a Triangle",
                        "Base:",
                        "Height:");

        if (values == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Triangle(
                            values[0],
                            values[1]));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests three side lengths and displays a triangle.
     */
    private void showThreeSideTriangleDialog() {
        double[] values =
                inputDialog.requestThreeValues(
                        "Triangle",
                        "Side A:",
                        "Side B:",
                        "Side C:");

        if (values == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Triangle(
                            values[0],
                            values[1],
                            values[2]));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }
}
