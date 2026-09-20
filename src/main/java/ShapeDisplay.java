import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Displays a dimension-aware visualization of a geometric shape.
 *
 * <p>The visualization is drawn programmatically with Java2D rather than
 * loaded from external image files. Shape dimensions determine the relative
 * proportions of the drawing while automatic scaling keeps the visualization
 * readable within a consistent display area.</p>
 *
 * <p>Two-dimensional shapes are drawn directly from their measurements.
 * Three-dimensional shapes use schematic projections that communicate their
 * dimensions without attempting full three-dimensional rendering.</p>
 */
public class ShapeDisplay {

    private static final int DRAWING_WIDTH = 540;
    private static final int DRAWING_HEIGHT = 400;

    private final JFrame frame;

    /**
     * Creates and displays a visualization for the supplied shape.
     *
     * @param shape shape to visualize
     * @throws IllegalArgumentException if the shape type is unsupported
     */
    public ShapeDisplay(Shape shape) {
        if (!isSupported(shape)) {
            throw new IllegalArgumentException(
                    "Unsupported shape type: "
                            + (shape == null
                            ? "null"
                            : shape.getClass().getSimpleName()));
        }

        frame = new JFrame(getShapeName(shape) + " Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(
                getShapeName(shape),
                SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new BorderLayout(25, 0));
        resultPanel.add(createCalculationPanel(shape), BorderLayout.WEST);
        resultPanel.add(new ShapePanel(shape), BorderLayout.CENTER);

        contentPanel.add(resultPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the calculation summary displayed beside the visualization.
     *
     * @param shape shape whose calculation is being displayed
     * @return configured calculation panel
     */
    private JPanel createCalculationPanel(Shape shape) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(270, DRAWING_HEIGHT));
        javax.swing.border.TitledBorder calculationBorder =
                BorderFactory.createTitledBorder("Calculation");
        calculationBorder.setTitleFont(
                new Font("Arial", Font.BOLD, 20));

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        calculationBorder,
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel calculationLabel =
                new JLabel(buildCalculationHtml(shape));
        calculationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        calculationLabel.setVerticalAlignment(SwingConstants.TOP);

        panel.add(calculationLabel, BorderLayout.NORTH);
        return panel;
    }

    /**
     * Builds the dimensions, formula, and result shown for a shape.
     *
     * @param shape shape being displayed
     * @return HTML-formatted calculation summary
     */
    private String buildCalculationHtml(Shape shape) {
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
    private String formatValue(double value) {
        if (value == Math.rint(value)) {
            return String.format("%.0f", value);
        }

        return String.format("%.2f", value);
    }

    /**
     * Determines whether a shape has a visualization supported by this class.
     *
     * @param shape shape to inspect
     * @return {@code true} when the shape can be visualized
     */
    private boolean isSupported(Shape shape) {
        return shape instanceof Circle
                || shape instanceof Rectangle
                || shape instanceof Square
                || shape instanceof Triangle
                || shape instanceof Sphere
                || shape instanceof Cube
                || shape instanceof Cone
                || shape instanceof Cylinder
                || shape instanceof Torus;
    }

    /**
     * Returns a user-friendly name for a supported shape.
     *
     * @param shape shape being displayed
     * @return display name
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

        throw new IllegalArgumentException("Unsupported shape type.");
    }

    /**
     * Draws the supplied shape and its dimension labels.
     */
    private static class ShapePanel extends JPanel {

        private static final int LEFT = 80;
        private static final int TOP = 55;
        private static final int AVAILABLE_WIDTH = 460;
        private static final int AVAILABLE_HEIGHT = 300;

        private static final Font DIMENSION_FONT =
                new Font("Arial", Font.PLAIN, 15);

        private final Shape shape;

        /**
         * Creates a drawing panel for a shape.
         *
         * @param shape shape to draw
         */
        ShapePanel(Shape shape) {
            this.shape = shape;
            setPreferredSize(
                    new Dimension(DRAWING_WIDTH, DRAWING_HEIGHT));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        /**
         * Draws the shape using antialiased Java2D graphics.
         *
         * @param graphics Swing graphics context
         */
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D g2 = (Graphics2D) graphics.create();
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

                if (shape instanceof Circle circle) {
                    drawCircle(g2, circle);
                } else if (shape instanceof Rectangle rectangle) {
                    drawRectangle(g2, rectangle);
                } else if (shape instanceof Square square) {
                    drawSquare(g2, square);
                } else if (shape instanceof Triangle triangle) {
                    drawTriangle(g2, triangle);
                } else if (shape instanceof Sphere sphere) {
                    drawSphere(g2, sphere);
                } else if (shape instanceof Cube cube) {
                    drawCube(g2, cube);
                } else if (shape instanceof Cone cone) {
                    drawCone(g2, cone);
                } else if (shape instanceof Cylinder cylinder) {
                    drawCylinder(g2, cylinder);
                } else if (shape instanceof Torus torus) {
                    drawTorus(g2, torus);
                }
            } finally {
                g2.dispose();
            }
        }

        /**
         * Draws a circle and labels its radius.
         */
        private void drawCircle(Graphics2D g2, Circle circle) {
            double diameter = circle.getRadius() * 2.0;
            double size = scaleUniform(diameter, diameter);
            double x = centeredX(size);
            double y = centeredY(size);

            Ellipse2D shape =
                    new Ellipse2D.Double(x, y, size, size);

            fillTwoDimensionalShape(g2, shape, y, y + size);

            double centerX = x + size / 2.0;
            double centerY = y + size / 2.0;

            g2.setColor(new Color(95, 95, 95));
            g2.setStroke(new BasicStroke(1.4f));

            g2.draw(new Line2D.Double(
                    centerX,
                    centerY,
                    x + size,
                    centerY));

            g2.fill(new Ellipse2D.Double(
                    centerX - 2.5,
                    centerY - 2.5,
                    5.0,
                    5.0));

            drawCenteredText(
                    g2,
                    "Radius = " + format(circle.getRadius()),
                    centerX + size / 4.0,
                    centerY - 10);
        }

        /**
         * Draws a rectangle using its length-to-width ratio.
         */
        private void drawRectangle(
                Graphics2D g2,
                Rectangle rectangle) {

            Dimension2D scaled = scale(
                    rectangle.getLength(),
                    rectangle.getWidth());

            double x = centeredX(scaled.width());
            double y = centeredY(scaled.height());

            Rectangle2D shape = new Rectangle2D.Double(
                    x,
                    y,
                    scaled.width(),
                    scaled.height());

            fillTwoDimensionalShape(
                    g2,
                    shape,
                    y,
                    y + scaled.height());

            drawHorizontalDimension(
                    g2,
                    x,
                    y + scaled.height(),
                    scaled.width(),
                    "Length = " + format(rectangle.getLength()));

            drawVerticalDimension(
                    g2,
                    x,
                    y,
                    scaled.height(),
                    "Width = " + format(rectangle.getWidth()));
        }

        /**
         * Draws a square and labels its side length.
         */
        private void drawSquare(Graphics2D g2, Square square) {
            double size = scaleUniform(
                    square.getSide(),
                    square.getSide());

            double x = centeredX(size);
            double y = centeredY(size);

            Rectangle2D shape =
                    new Rectangle2D.Double(x, y, size, size);

            fillTwoDimensionalShape(g2, shape, y, y + size);

            drawHorizontalDimension(
                    g2,
                    x,
                    y + size,
                    size,
                    "Side = " + format(square.getSide()));
        }

        /**
         * Draws either a base-height triangle or a triangle constructed from
         * three side lengths.
         */
        private void drawTriangle(Graphics2D g2, Triangle triangle) {
            if (triangle.isDefinedByThreeSides()) {
                drawThreeSideTriangle(g2, triangle);
            } else {
                drawBaseHeightTriangle(g2, triangle);
            }
        }

        /**
         * Draws a triangle using its base and height.
         */
        private void drawBaseHeightTriangle(
                Graphics2D g2,
                Triangle triangle) {

            Dimension2D scaled = scale(
                    triangle.getBase(),
                    triangle.getHeight());

            double x = centeredX(scaled.width());
            double y = centeredY(scaled.height());

            double leftX = x;
            double rightX = x + scaled.width();
            double bottomY = y + scaled.height();
            double apexX = x + scaled.width() / 2.0;

            Path2D path = new Path2D.Double();
            path.moveTo(leftX, bottomY);
            path.lineTo(rightX, bottomY);
            path.lineTo(apexX, y);
            path.closePath();

            fillTwoDimensionalShape(g2, path, y, bottomY);

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

            drawHorizontalDimension(
                    g2,
                    leftX,
                    bottomY,
                    scaled.width(),
                    "Base = " + format(triangle.getBase()));

            drawText(
                    g2,
                    "Height = " + format(triangle.getHeight()),
                    apexX + 10,
                    y + scaled.height() / 2.0);
        }

        /**
         * Draws a triangle with proportions derived from its three sides.
         */
        private void drawThreeSideTriangle(
                Graphics2D g2,
                Triangle triangle) {

            double a = triangle.getSideA();
            double b = triangle.getSideB();
            double c = triangle.getSideC();

            double apexXFromLeft =
                    (b * b + c * c - a * a) / (2.0 * c);

            double rawHeight = Math.sqrt(
                    Math.max(
                            0.0,
                            b * b
                                    - apexXFromLeft
                                    * apexXFromLeft));

            double scale = Math.min(
                    AVAILABLE_WIDTH / c,
                    AVAILABLE_HEIGHT / rawHeight);

            double baseWidth = c * scale;
            double height = rawHeight * scale;
            double apexOffset = apexXFromLeft * scale;

            double x = centeredX(baseWidth);
            double y = centeredY(height);
            double bottomY = y + height;
            double leftX = x;
            double rightX = x + baseWidth;
            double apexX = x + apexOffset;

            Path2D path = new Path2D.Double();
            path.moveTo(leftX, bottomY);
            path.lineTo(rightX, bottomY);
            path.lineTo(apexX, y);
            path.closePath();

            fillTwoDimensionalShape(g2, path, y, bottomY);

            g2.setColor(Color.BLACK);

            drawText(
                    g2,
                    "A = " + format(a),
                    (rightX + apexX) / 2.0 + 8,
                    (bottomY + y) / 2.0);

            drawText(
                    g2,
                    "B = " + format(b),
                    (leftX + apexX) / 2.0 - 65,
                    (bottomY + y) / 2.0);

            drawHorizontalDimension(
                    g2,
                    leftX,
                    bottomY,
                    baseWidth,
                    "C = " + format(c));

            drawCenteredText(
                    g2,
                    triangle.determineTriangleType(),
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT + 70);
        }

        /**
         * Fills a two-dimensional shape with a subtle technical-diagram
         * gradient and then draws a clean outline around its boundary.
         *
         * @param g2 graphics context used for rendering
         * @param shape shape to fill and outline
         * @param top top coordinate of the shape
         * @param bottom bottom coordinate of the shape
         */
        private void fillTwoDimensionalShape(
                Graphics2D g2,
                java.awt.Shape shape,
                double top,
                double bottom) {

            Paint previousPaint = g2.getPaint();
            java.awt.Stroke previousStroke = g2.getStroke();

            GradientPaint gradient = new GradientPaint(
                    0.0f,
                    (float) top,
                    new Color(225, 240, 250),
                    0.0f,
                    (float) bottom,
                    new Color(155, 195, 220));

            g2.setPaint(gradient);
            g2.fill(shape);

            g2.setPaint(new Color(65, 95, 115));
            g2.setStroke(new BasicStroke(1.6f));
            g2.draw(shape);

            g2.setPaint(previousPaint);
            g2.setStroke(previousStroke);
        }

        /**
         * Draws a sphere as a circle with curved guide ellipses.
         */
        private void drawSphere(Graphics2D g2, Sphere sphere) {
            double radius = sphere.getRadius();

            int latitudeSegments = 24;
            int longitudeSegments = 48;

            double rotationX = Math.toRadians(25.0);
            double rotationY = Math.toRadians(-35.0);
            double rotationZ = Math.toRadians(-8.0);

            double scale = Math.min(
                    AVAILABLE_WIDTH * 0.38 / radius,
                    AVAILABLE_HEIGHT * 0.38 / radius);

            double centerX = getWidth() / 2.0;
            double centerY = TOP + AVAILABLE_HEIGHT / 2.0;

            List<SurfacePatch> patches = new ArrayList<>();

            for (int latitude = 0;
                    latitude < latitudeSegments;
                    latitude++) {

                double phi0 =
                        -Math.PI / 2.0
                                + Math.PI * latitude
                                / latitudeSegments;

                double phi1 =
                        -Math.PI / 2.0
                                + Math.PI * (latitude + 1)
                                / latitudeSegments;

                for (int longitude = 0;
                        longitude < longitudeSegments;
                        longitude++) {

                    double theta0 =
                            2.0 * Math.PI * longitude
                                    / longitudeSegments;

                    double theta1 =
                            2.0 * Math.PI * (longitude + 1)
                                    / longitudeSegments;

                    Point3D p0 = spherePoint(
                            radius, phi0, theta0);
                    Point3D p1 = spherePoint(
                            radius, phi0, theta1);
                    Point3D p2 = spherePoint(
                            radius, phi1, theta1);
                    Point3D p3 = spherePoint(
                            radius, phi1, theta0);

                    addSurfacePatch(
                            patches,
                            p0,
                            p1,
                            p2,
                            p3,
                            rotationX,
                            rotationY,
                            rotationZ);
                }
            }

            drawSurfacePatches(
                    g2,
                    patches,
                    centerX,
                    centerY,
                    scale);

            g2.setColor(Color.BLACK);

            drawCenteredText(
                    g2,
                    "Radius = " + format(radius),
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT + 35);
        }

        /**
         * Calculates one point on a spherical surface.
         */
        private Point3D spherePoint(
                double radius,
                double latitude,
                double longitude) {

            double cosLatitude = Math.cos(latitude);

            return new Point3D(
                    radius * cosLatitude * Math.cos(longitude),
                    radius * cosLatitude * Math.sin(longitude),
                    radius * Math.sin(latitude));
        }

        /**
         * Draws a cube using a simple oblique projection.
         */
        private void drawCube(Graphics2D g2, Cube cube) {
            double side = cube.getSide();
            double half = side / 2.0;
            int divisions = 10;

            double rotationX = Math.toRadians(25.0);
            double rotationY = Math.toRadians(-35.0);
            double rotationZ = Math.toRadians(-8.0);

            List<SurfacePatch> patches = new ArrayList<>();

            /*
             * Subdivide each cube face into a regular mesh so the cube uses
             * the same shaded-surface visual treatment as the curved solids.
             */
            for (int face = 0; face < 6; face++) {
                for (int row = 0; row < divisions; row++) {
                    for (int column = 0; column < divisions; column++) {
                        double a0 =
                                -half + side * column / divisions;
                        double a1 =
                                -half + side * (column + 1) / divisions;
                        double b0 =
                                -half + side * row / divisions;
                        double b1 =
                                -half + side * (row + 1) / divisions;

                        Point3D p0;
                        Point3D p1;
                        Point3D p2;
                        Point3D p3;

                        switch (face) {
                            case 0 -> {
                                p0 = new Point3D(a0, b0, -half);
                                p1 = new Point3D(a1, b0, -half);
                                p2 = new Point3D(a1, b1, -half);
                                p3 = new Point3D(a0, b1, -half);
                            }
                            case 1 -> {
                                p0 = new Point3D(a0, b0, half);
                                p1 = new Point3D(a0, b1, half);
                                p2 = new Point3D(a1, b1, half);
                                p3 = new Point3D(a1, b0, half);
                            }
                            case 2 -> {
                                p0 = new Point3D(a0, -half, b0);
                                p1 = new Point3D(a0, -half, b1);
                                p2 = new Point3D(a1, -half, b1);
                                p3 = new Point3D(a1, -half, b0);
                            }
                            case 3 -> {
                                p0 = new Point3D(half, a0, b0);
                                p1 = new Point3D(half, a1, b0);
                                p2 = new Point3D(half, a1, b1);
                                p3 = new Point3D(half, a0, b1);
                            }
                            case 4 -> {
                                p0 = new Point3D(a0, half, b0);
                                p1 = new Point3D(a1, half, b0);
                                p2 = new Point3D(a1, half, b1);
                                p3 = new Point3D(a0, half, b1);
                            }
                            default -> {
                                p0 = new Point3D(-half, a0, b0);
                                p1 = new Point3D(-half, a0, b1);
                                p2 = new Point3D(-half, a1, b1);
                                p3 = new Point3D(-half, a1, b0);
                            }
                        }

                        addSurfacePatch(
                                patches,
                                p0,
                                p1,
                                p2,
                                p3,
                                rotationX,
                                rotationY,
                                rotationZ);
                    }
                }
            }

            double scale = Math.min(
                    AVAILABLE_WIDTH * 0.52 / side,
                    AVAILABLE_HEIGHT * 0.52 / side);

            drawSurfacePatches(
                    g2,
                    patches,
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT / 2.0,
                    scale);

            g2.setColor(Color.BLACK);
            drawCenteredText(
                    g2,
                    "Side = " + format(side),
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT + 35);
        }

        /**
         * Draws a cone with a dimension-aware height-to-diameter ratio.
         */
        private void drawCone(Graphics2D g2, Cone cone) {
            double radius = cone.getRadius();
            double height = cone.getHeight();

            int segments = 56;

            double rotationX = Math.toRadians(25.0);
            double rotationY = Math.toRadians(-35.0);
            double rotationZ = Math.toRadians(-8.0);

            List<SurfacePatch> patches = new ArrayList<>();

            Point3D apex = new Point3D(
                    0.0,
                    0.0,
                    height / 2.0);

            Point3D baseCenter = new Point3D(
                    0.0,
                    0.0,
                    -height / 2.0);

            for (int i = 0; i < segments; i++) {
                double angle0 =
                        2.0 * Math.PI * i / segments;

                double angle1 =
                        2.0 * Math.PI * (i + 1) / segments;

                Point3D base0 = new Point3D(
                        radius * Math.cos(angle0),
                        radius * Math.sin(angle0),
                        -height / 2.0);

                Point3D base1 = new Point3D(
                        radius * Math.cos(angle1),
                        radius * Math.sin(angle1),
                        -height / 2.0);

                addTrianglePatch(
                        patches,
                        apex,
                        base0,
                        base1,
                        rotationX,
                        rotationY,
                        rotationZ);

                addTrianglePatch(
                        patches,
                        baseCenter,
                        base1,
                        base0,
                        rotationX,
                        rotationY,
                        rotationZ);
            }

            double diameter = radius * 2.0;

            double scale = Math.min(
                    AVAILABLE_WIDTH * 0.62 / diameter,
                    AVAILABLE_HEIGHT * 0.62 / height);

            drawSurfacePatches(
                    g2,
                    patches,
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT / 2.0,
                    scale);

            g2.setColor(Color.BLACK);

            drawCenteredText(
                    g2,
                    "Radius = " + format(radius)
                            + "    Height = " + format(height),
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT + 35);
        }

        /**
         * Draws a cylinder with a dimension-aware height-to-diameter ratio.
         */
        private void drawCylinder(
                Graphics2D g2,
                Cylinder cylinder) {

            double radius = cylinder.getRadius();
            double height = cylinder.getHeight();
            int radialSegments = 56;
            int heightSegments = 12;
            int capSegments = 10;

            double rotationX = Math.toRadians(25.0);
            double rotationY = Math.toRadians(-35.0);
            double rotationZ = Math.toRadians(-8.0);

            List<SurfacePatch> patches = new ArrayList<>();

            /*
             * Subdivide the curved wall vertically so its mesh density and
             * shaded appearance match the other three-dimensional solids.
             */
            for (int i = 0; i < radialSegments; i++) {
                double angle0 =
                        2.0 * Math.PI * i / radialSegments;
                double angle1 =
                        2.0 * Math.PI * (i + 1) / radialSegments;

                for (int row = 0; row < heightSegments; row++) {
                    double z0 =
                            -height / 2.0
                                    + height * row / heightSegments;
                    double z1 =
                            -height / 2.0
                                    + height * (row + 1)
                                    / heightSegments;

                    Point3D p0 = new Point3D(
                            radius * Math.cos(angle0),
                            radius * Math.sin(angle0),
                            z0);
                    Point3D p1 = new Point3D(
                            radius * Math.cos(angle1),
                            radius * Math.sin(angle1),
                            z0);
                    Point3D p2 = new Point3D(
                            radius * Math.cos(angle1),
                            radius * Math.sin(angle1),
                            z1);
                    Point3D p3 = new Point3D(
                            radius * Math.cos(angle0),
                            radius * Math.sin(angle0),
                            z1);

                    addSurfacePatch(
                            patches,
                            p0,
                            p1,
                            p2,
                            p3,
                            rotationX,
                            rotationY,
                            rotationZ);
                }

                /*
                 * Build each circular cap as concentric rings rather than
                 * large center triangles, producing a consistent mesh.
                 */
                for (int ring = 0; ring < capSegments; ring++) {
                    double inner =
                            radius * ring / capSegments;
                    double outer =
                            radius * (ring + 1) / capSegments;

                    Point3D top0 = new Point3D(
                            inner * Math.cos(angle0),
                            inner * Math.sin(angle0),
                            height / 2.0);
                    Point3D top1 = new Point3D(
                            outer * Math.cos(angle0),
                            outer * Math.sin(angle0),
                            height / 2.0);
                    Point3D top2 = new Point3D(
                            outer * Math.cos(angle1),
                            outer * Math.sin(angle1),
                            height / 2.0);
                    Point3D top3 = new Point3D(
                            inner * Math.cos(angle1),
                            inner * Math.sin(angle1),
                            height / 2.0);

                    addSurfacePatch(
                            patches,
                            top0,
                            top1,
                            top2,
                            top3,
                            rotationX,
                            rotationY,
                            rotationZ);

                    Point3D bottom0 = new Point3D(
                            inner * Math.cos(angle0),
                            inner * Math.sin(angle0),
                            -height / 2.0);
                    Point3D bottom1 = new Point3D(
                            inner * Math.cos(angle1),
                            inner * Math.sin(angle1),
                            -height / 2.0);
                    Point3D bottom2 = new Point3D(
                            outer * Math.cos(angle1),
                            outer * Math.sin(angle1),
                            -height / 2.0);
                    Point3D bottom3 = new Point3D(
                            outer * Math.cos(angle0),
                            outer * Math.sin(angle0),
                            -height / 2.0);

                    addSurfacePatch(
                            patches,
                            bottom0,
                            bottom1,
                            bottom2,
                            bottom3,
                            rotationX,
                            rotationY,
                            rotationZ);
                }
            }

            double diameter = radius * 2.0;
            double scale = Math.min(
                    AVAILABLE_WIDTH * 0.62 / diameter,
                    AVAILABLE_HEIGHT * 0.62 / height);

            drawSurfacePatches(
                    g2,
                    patches,
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT / 2.0,
                    scale);

            g2.setColor(Color.BLACK);
            drawCenteredText(
                    g2,
                    "Radius = " + format(radius)
                            + "    Height = " + format(height),
                    getWidth() / 2.0,
                    TOP + AVAILABLE_HEIGHT + 35);
        }

        /**
         * Draws a torus using concentric ellipses whose proportions reflect
         * the major and minor radii.
         */
        /**
         * Draws a torus from an actual three-dimensional parametric surface.
         *
         * <p>The torus is generated in model space, rotated to an oblique
         * viewing angle, projected onto the Swing drawing surface, and
         * rendered back-to-front. Surface lighting is calculated from each
         * patch normal so the tube curvature and center opening remain
         * visually apparent.</p>
         */
        private void drawTorus(Graphics2D g2, Torus torus) {
            double majorRadius = torus.getMajorRadius();
            double minorRadius = torus.getMinorRadius();

            int majorSegments = 64;
            int minorSegments = 28;

            double rotationX = Math.toRadians(25.0);
            double rotationY = Math.toRadians(-35.0);
            double rotationZ = Math.toRadians(-8.0);

            double modelRadius = majorRadius + minorRadius;
            double scale = Math.min(
                    AVAILABLE_WIDTH * 0.42 / modelRadius,
                    AVAILABLE_HEIGHT * 0.42 / modelRadius);

            double centerX = getWidth() / 2.0;
            double centerY = TOP + AVAILABLE_HEIGHT / 2.0;

            List<SurfacePatch> patches = new ArrayList<>();

            for (int i = 0; i < majorSegments; i++) {
                double u0 = 2.0 * Math.PI * i / majorSegments;
                double u1 = 2.0 * Math.PI * (i + 1) / majorSegments;

                for (int j = 0; j < minorSegments; j++) {
                    double v0 = 2.0 * Math.PI * j / minorSegments;
                    double v1 = 2.0 * Math.PI * (j + 1) / minorSegments;

                    Point3D p0 = torusPoint(
                            majorRadius, minorRadius, u0, v0);
                    Point3D p1 = torusPoint(
                            majorRadius, minorRadius, u1, v0);
                    Point3D p2 = torusPoint(
                            majorRadius, minorRadius, u1, v1);
                    Point3D p3 = torusPoint(
                            majorRadius, minorRadius, u0, v1);

                    p0 = rotate(p0, rotationX, rotationY, rotationZ);
                    p1 = rotate(p1, rotationX, rotationY, rotationZ);
                    p2 = rotate(p2, rotationX, rotationY, rotationZ);
                    p3 = rotate(p3, rotationX, rotationY, rotationZ);

                    Point3D edgeA = subtract(p1, p0);
                    Point3D edgeB = subtract(p3, p0);
                    Point3D normal = normalize(cross(edgeA, edgeB));

                    double light = Math.max(
                            0.0,
                            dot(
                                    normal,
                                    normalize(new Point3D(
                                            -0.35,
                                            -0.55,
                                            1.0))));

                    double depth =
                            (p0.z() + p1.z() + p2.z() + p3.z()) / 4.0;

                    patches.add(new SurfacePatch(
                            new Point3D[]{p0, p1, p2, p3},
                            depth,
                            light));
                }
            }

            patches.sort(
                    Comparator.comparingDouble(SurfacePatch::depth));

            g2.setStroke(new BasicStroke(0.35f));

            for (SurfacePatch patch : patches) {
                Path2D polygon = new Path2D.Double();

                for (int i = 0; i < patch.points().length; i++) {
                    Point3D point = patch.points()[i];

                    double screenX = centerX + point.x() * scale;
                    double screenY = centerY - point.y() * scale;

                    if (i == 0) {
                        polygon.moveTo(screenX, screenY);
                    } else {
                        polygon.lineTo(screenX, screenY);
                    }
                }

                polygon.closePath();

                double light = Math.max(
                        0.0,
                        Math.min(1.0, patch.light()));

                int red = (int) Math.round(
                        105 + light * 120);
                int green = (int) Math.round(
                        155 + light * 85);
                int blue = (int) Math.round(
                        190 + light * 60);

                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                g2.setColor(new Color(red, green, blue));
                g2.fill(polygon);

                g2.setColor(new Color(
                        Math.max(55, red - 50),
                        Math.max(75, green - 50),
                        Math.max(95, blue - 50)));

                g2.draw(polygon);
            }

            /*
             * Dimension labels describe the mathematical torus rather than
             * the scaled screen representation.
             */
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.5f));

            double labelY = TOP + AVAILABLE_HEIGHT + 35;

            drawCenteredText(
                    g2,
                    "Major radius = " + format(majorRadius)
                            + "    Minor radius = " + format(minorRadius),
                    getWidth() / 2.0,
                    labelY);
        }

        /**
         * Rotates four model-space points and stores them as one surface patch.
         */
        private void addSurfacePatch(
                List<SurfacePatch> patches,
                Point3D p0,
                Point3D p1,
                Point3D p2,
                Point3D p3,
                double rotationX,
                double rotationY,
                double rotationZ) {

            p0 = rotate(p0, rotationX, rotationY, rotationZ);
            p1 = rotate(p1, rotationX, rotationY, rotationZ);
            p2 = rotate(p2, rotationX, rotationY, rotationZ);
            p3 = rotate(p3, rotationX, rotationY, rotationZ);

            Point3D normal = normalize(
                    cross(
                            subtract(p1, p0),
                            subtract(p3, p0)));

            double depth =
                    (p0.z() + p1.z() + p2.z() + p3.z()) / 4.0;

            patches.add(new SurfacePatch(
                    new Point3D[]{p0, p1, p2, p3},
                    depth,
                    surfaceLight(normal)));
        }

        /**
         * Rotates three model-space points and stores them as one triangular
         * surface patch.
         */
        private void addTrianglePatch(
                List<SurfacePatch> patches,
                Point3D p0,
                Point3D p1,
                Point3D p2,
                double rotationX,
                double rotationY,
                double rotationZ) {

            p0 = rotate(p0, rotationX, rotationY, rotationZ);
            p1 = rotate(p1, rotationX, rotationY, rotationZ);
            p2 = rotate(p2, rotationX, rotationY, rotationZ);

            Point3D normal = normalize(
                    cross(
                            subtract(p1, p0),
                            subtract(p2, p0)));

            double depth =
                    (p0.z() + p1.z() + p2.z()) / 3.0;

            patches.add(new SurfacePatch(
                    new Point3D[]{p0, p1, p2},
                    depth,
                    surfaceLight(normal)));
        }

        /**
         * Calculates diffuse lighting for a projected surface normal.
         */
        private double surfaceLight(Point3D normal) {
            double diffuse = Math.max(
                    0.0,
                    dot(
                            normal,
                            normalize(new Point3D(
                                    -0.35,
                                    -0.55,
                                    1.0))));

            /*
             * Ambient light keeps surfaces readable even when they face away
             * from the primary light source, while diffuse light preserves
             * the depth cues needed to distinguish individual faces.
             */
            return 0.55 + 0.45 * diffuse;
        }

        /**
         * Draws depth-sorted three-dimensional surface patches.
         */
        private void drawSurfacePatches(
                Graphics2D g2,
                List<SurfacePatch> patches,
                double centerX,
                double centerY,
                double scale) {

            patches.sort(
                    Comparator.comparingDouble(SurfacePatch::depth));

            g2.setStroke(new BasicStroke(0.55f));

            for (SurfacePatch patch : patches) {
                Path2D polygon = new Path2D.Double();

                for (int i = 0; i < patch.points().length; i++) {
                    Point3D point = patch.points()[i];

                    double screenX =
                            centerX + point.x() * scale;

                    double screenY =
                            centerY - point.y() * scale;

                    if (i == 0) {
                        polygon.moveTo(screenX, screenY);
                    } else {
                        polygon.lineTo(screenX, screenY);
                    }
                }

                polygon.closePath();

                double light = Math.max(
                        0.0,
                        Math.min(1.0, patch.light()));

                int red = (int) Math.round(
                        105 + light * 120);
                int green = (int) Math.round(
                        155 + light * 85);
                int blue = (int) Math.round(
                        190 + light * 60);

                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                g2.setColor(new Color(red, green, blue));
                g2.fill(polygon);

                g2.setColor(new Color(
                        Math.max(55, red - 50),
                        Math.max(75, green - 50),
                        Math.max(95, blue - 50)));

                g2.draw(polygon);
            }
        }

        /**
         * Calculates one point on a torus parametric surface.
         */
        private Point3D torusPoint(
                double majorRadius,
                double minorRadius,
                double u,
                double v) {

            double ring = majorRadius + minorRadius * Math.cos(v);

            return new Point3D(
                    ring * Math.cos(u),
                    ring * Math.sin(u),
                    minorRadius * Math.sin(v));
        }

        /**
         * Rotates a point around the x-axis and then the z-axis.
         */
        /**
         * Rotates a point around the x, y, and z axes.
         */
        private Point3D rotate(
                Point3D point,
                double rotationX,
                double rotationY,
                double rotationZ) {

            double cosX = Math.cos(rotationX);
            double sinX = Math.sin(rotationX);

            double x1 = point.x();
            double y1 =
                    point.y() * cosX
                            - point.z() * sinX;
            double z1 =
                    point.y() * sinX
                            + point.z() * cosX;

            double cosY = Math.cos(rotationY);
            double sinY = Math.sin(rotationY);

            double x2 =
                    x1 * cosY
                            + z1 * sinY;
            double y2 = y1;
            double z2 =
                    -x1 * sinY
                            + z1 * cosY;

            double cosZ = Math.cos(rotationZ);
            double sinZ = Math.sin(rotationZ);

            return new Point3D(
                    x2 * cosZ - y2 * sinZ,
                    x2 * sinZ + y2 * cosZ,
                    z2);
        }

        /**
         * Subtracts one three-dimensional vector from another.
         */
        private Point3D subtract(Point3D a, Point3D b) {
            return new Point3D(
                    a.x() - b.x(),
                    a.y() - b.y(),
                    a.z() - b.z());
        }

        /**
         * Calculates the cross product of two three-dimensional vectors.
         */
        private Point3D cross(Point3D a, Point3D b) {
            return new Point3D(
                    a.y() * b.z() - a.z() * b.y(),
                    a.z() * b.x() - a.x() * b.z(),
                    a.x() * b.y() - a.y() * b.x());
        }

        /**
         * Calculates the dot product of two three-dimensional vectors.
         */
        private double dot(Point3D a, Point3D b) {
            return a.x() * b.x()
                    + a.y() * b.y()
                    + a.z() * b.z();
        }

        /**
         * Returns a unit-length version of a three-dimensional vector.
         */
        private Point3D normalize(Point3D point) {
            double length = Math.sqrt(
                    point.x() * point.x()
                            + point.y() * point.y()
                            + point.z() * point.z());

            if (length == 0.0) {
                return new Point3D(0.0, 0.0, 0.0);
            }

            return new Point3D(
                    point.x() / length,
                    point.y() / length,
                    point.z() / length);
        }

        /**
         * Represents a point or vector in three-dimensional model space.
         */
        private record Point3D(double x, double y, double z) {
        }

        /**
         * Represents one depth-sortable surface patch.
         */
        private record SurfacePatch(
                Point3D[] points,
                double depth,
                double light) {
        }

        /**
         * Scales two dimensions proportionally to fit the drawing area.
         *
         * @param width logical width
         * @param height logical height
         * @return scaled dimensions
         */
        private Dimension2D scale(double width, double height) {
            double scale = Math.min(
                    AVAILABLE_WIDTH / width,
                    AVAILABLE_HEIGHT / height);

            return new Dimension2D(
                    width * scale,
                    height * scale);
        }

        /**
         * Returns the largest uniform size that fits the drawing area.
         */
        private double scaleUniform(double width, double height) {
            Dimension2D scaled = scale(width, height);
            return Math.min(scaled.width(), scaled.height());
        }

        /**
         * Centers a drawing horizontally in the panel.
         */
        private double centeredX(double width) {
            return (getWidth() - width) / 2.0;
        }

        /**
         * Centers a drawing vertically in the primary drawing area.
         */
        private double centeredY(double height) {
            return TOP + (AVAILABLE_HEIGHT - height) / 2.0;
        }

        /**
         * Draws a horizontal dimension label beneath a measurement.
         */
        private void drawHorizontalDimension(
                Graphics2D g2,
                double x,
                double y,
                double width,
                String label) {

            double labelX = x + width / 2.0;
            drawCenteredText(g2, label, labelX, y + 28);
        }

        /**
         * Draws a vertical dimension label beside a measurement.
         */
        private void drawVerticalDimension(
                Graphics2D g2,
                double x,
                double y,
                double height,
                String label) {

            drawText(
                    g2,
                    label,
                    Math.max(8, x - 75),
                    y + height / 2.0);
        }

        /**
         * Draws text centered around the supplied x-coordinate.
         */
        private void drawCenteredText(
                Graphics2D g2,
                String text,
                double centerX,
                double y) {

            FontMetrics metrics = g2.getFontMetrics();
            double x = centerX - metrics.stringWidth(text) / 2.0;
            drawText(g2, text, x, y);
        }

        /**
         * Draws text at the supplied coordinates.
         */
        private void drawText(
                Graphics2D g2,
                String text,
                double x,
                double y) {

            g2.drawString(
                    text,
                    (float) x,
                    (float) y);
        }

        /**
         * Formats a dimension without unnecessary trailing zeros.
         */
        private String format(double value) {
            if (value == Math.rint(value)) {
                return String.format("%.0f", value);
            }

            return String.format("%.2f", value);
        }

        /**
         * Holds scaled drawing dimensions without relying on integer-based
         * AWT dimension classes.
         *
         * @param width scaled width
         * @param height scaled height
         */
        private record Dimension2D(double width, double height) {
        }
    }
}
