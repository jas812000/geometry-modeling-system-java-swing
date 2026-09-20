package ui.workflow;

import geometry.threedimensional.Cone;
import geometry.threedimensional.Cube;
import geometry.threedimensional.Cylinder;
import geometry.threedimensional.Sphere;
import geometry.threedimensional.Torus;
import ui.ShapeDisplay;
import ui.input.ShapeInputDialog;

import java.awt.*;

/**
 * Coordinates user-input workflows for three-dimensional shapes.
 */
public class ThreeDimensionalShapeWorkflow {

    private final ShapeInputDialog inputDialog;

    /**
     * Creates the three-dimensional shape workflow.
     *
     * @param parent parent component for dialogs
     */
    public ThreeDimensionalShapeWorkflow(Component parent) {
        this.inputDialog =
                new ShapeInputDialog(parent);
    }

    /**
     * Requests a radius and displays a sphere.
     */
    public void showSphereDialog() {
        Double radius =
                inputDialog.requestSingleValue(
                        "Volume of a Sphere",
                        "Radius:");

        if (radius == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Sphere(radius));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests a side length and displays a cube.
     */
    public void showCubeDialog() {
        Double side =
                inputDialog.requestSingleValue(
                        "Volume of a Cube",
                        "Side:");

        if (side == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Cube(side));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests radius and height values and displays a cone.
     */
    public void showConeDialog() {
        double[] values =
                inputDialog.requestTwoValues(
                        "Volume of a Cone",
                        "Radius:",
                        "Height:");

        if (values == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Cone(
                            values[0],
                            values[1]));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests radius and height values and displays a cylinder.
     */
    public void showCylinderDialog() {
        double[] values =
                inputDialog.requestTwoValues(
                        "Volume of a Cylinder",
                        "Radius:",
                        "Height:");

        if (values == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Cylinder(
                            values[0],
                            values[1]));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }

    /**
     * Requests major and minor radii and displays a torus.
     */
    public void showTorusDialog() {
        double[] values =
                inputDialog.requestTwoValues(
                        "Volume of a Torus",
                        "Major Radius:",
                        "Minor Radius:");

        if (values == null) {
            return;
        }

        try {
            new ShapeDisplay(
                    new Torus(
                            values[0],
                            values[1]));

        } catch (IllegalArgumentException e) {
            inputDialog.showInputError(
                    e.getMessage());
        }
    }
}
