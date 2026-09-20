package ui.calculation;

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
import geometry.twodimensional.TwoDimensional;

/**
 * Builds the formatted calculation summary displayed for supported shapes.
 */
public final class ShapeCalculationFormatter {

    private ShapeCalculationFormatter() {
        // Prevent instantiation because this class contains only formatting logic.
    }

    /**
     * Builds the dimensions, formula, substitution, and result for a shape.
     *
     * @param shape shape whose calculation should be formatted
     * @return HTML-formatted calculation summary
     * @throws IllegalArgumentException if the shape type is unsupported
     */
    public static String buildCalculationHtml(Shape shape) {
        String dimensions;
        String formula;
        String substitution;
        String resultLabel;
        double result;

        if (shape instanceof Circle circle) {
            String radius = formatValue(circle.getRadius());
            dimensions = "Radius: " + radius;
            formula = "A = πr²";
            substitution = "A = π(" + radius + ")²";
            resultLabel = "Area";
            result = circle.getArea();

        } else if (shape instanceof Rectangle rectangle) {
            String length = formatValue(rectangle.getLength());
            String width = formatValue(rectangle.getWidth());

            dimensions =
                    "Length: " + length
                            + "<br>Width: " + width;
            formula = "A = l × w";
            substitution =
                    "A = " + length + " × " + width;
            resultLabel = "Area";
            result = rectangle.getArea();

        } else if (shape instanceof Square square) {
            String side = formatValue(square.getSide());

            dimensions = "Side: " + side;
            formula = "A = s²";
            substitution = "A = (" + side + ")²";
            resultLabel = "Area";
            result = square.getArea();

        } else if (shape instanceof Triangle triangle) {
            if (triangle.isDefinedByThreeSides()) {
                String a = formatValue(triangle.getSideA());
                String b = formatValue(triangle.getSideB());
                String c = formatValue(triangle.getSideC());

                double semiPerimeter =
                        (triangle.getSideA()
                                + triangle.getSideB()
                                + triangle.getSideC()) / 2.0;

                String semi = formatValue(semiPerimeter);

                dimensions =
                        "Side A: " + a
                                + "<br>Side B: " + b
                                + "<br>Side C: " + c
                                + "<br><br><b>Type</b><br>"
                                + triangle.determineTriangleType();

                formula =
                        "A = √(s(s − a)(s − b)(s − c))";

                substitution =
                        "s = (" + a + " + " + b + " + " + c + ") ÷ 2"
                                + "<br>s = " + semi
                                + "<br>A = √("
                                + semi + "("
                                + semi + " − " + a + ")("
                                + semi + " − " + b + ")("
                                + semi + " − " + c + "))";
            } else {
                String base = formatValue(triangle.getBase());
                String height = formatValue(triangle.getHeight());

                dimensions =
                        "Base: " + base
                                + "<br>Height: " + height;
                formula = "A = ½bh";
                substitution =
                        "A = ½(" + base + ")(" + height + ")";
            }

            resultLabel = "Area";
            result = triangle.getArea();

        } else if (shape instanceof Sphere sphere) {
            String radius = formatValue(sphere.getRadius());

            dimensions = "Radius: " + radius;
            formula = "V = ⁴⁄₃πr³";
            substitution =
                    "V = ⁴⁄₃π(" + radius + ")³";
            resultLabel = "Volume";
            result = sphere.getVolume();

        } else if (shape instanceof Cube cube) {
            String side = formatValue(cube.getSide());

            dimensions = "Side: " + side;
            formula = "V = s³";
            substitution = "V = (" + side + ")³";
            resultLabel = "Volume";
            result = cube.getVolume();

        } else if (shape instanceof Cone cone) {
            String radius = formatValue(cone.getRadius());
            String height = formatValue(cone.getHeight());

            dimensions =
                    "Radius: " + radius
                            + "<br>Height: " + height;
            formula = "V = ⅓πr²h";
            substitution =
                    "V = ⅓π(" + radius + ")²(" + height + ")";
            resultLabel = "Volume";
            result = cone.getVolume();

        } else if (shape instanceof Cylinder cylinder) {
            String radius = formatValue(cylinder.getRadius());
            String height = formatValue(cylinder.getHeight());

            dimensions =
                    "Radius: " + radius
                            + "<br>Height: " + height;
            formula = "V = πr²h";
            substitution =
                    "V = π(" + radius + ")²(" + height + ")";
            resultLabel = "Volume";
            result = cylinder.getVolume();

        } else if (shape instanceof Torus torus) {
            String major = formatValue(torus.getMajorRadius());
            String minor = formatValue(torus.getMinorRadius());

            dimensions =
                    "Major Radius: " + major
                            + "<br>Minor Radius: " + minor;
            formula = "V = 2π²Rr²";
            substitution =
                    "V = 2π²(" + major + ")(" + minor + ")²";
            resultLabel = "Volume";
            result = torus.getVolume();

        } else {
            throw new IllegalArgumentException(
                    "Unsupported shape type.");
        }

        String units =
                shape instanceof TwoDimensional
                        ? "square units"
                        : "cubic units";

        return "<html>"
                + "<b>Dimensions</b><br>"
                + dimensions
                + "<br><br>"
                + "<b>Formula</b><br>"
                + formula
                + "<br><br>"
                + "<b>Calculation</b><br>"
                + substitution
                + "<br><br>"
                + "<b>" + resultLabel + "</b><br>"
                + String.format("%.2f", result)
                + " " + units
                + "</html>";
    }

    /**
     * Formats a dimension without unnecessary trailing zeros.
     *
     * @param value dimension value
     * @return formatted dimension
     */
    private static String formatValue(double value) {
        if (value == Math.rint(value)) {
            return String.format("%.0f", value);
        }

        return String.format("%.2f", value);
    }
}
