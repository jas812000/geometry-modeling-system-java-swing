# Geometry Modeling System

A Java Swing desktop application for creating, calculating, and visualizing two-dimensional and three-dimensional geometric shapes.

The project demonstrates object-oriented design, domain validation, event-driven desktop development, Java2D rendering, automated testing, and separation of application responsibilities into focused packages and components.

## Features

- Models both 2D and 3D geometric shapes
- Calculates area or volume from user-provided dimensions
- Validates geometric measurements and relationships
- Aggregates invalid input so multiple fields can be corrected at once
- Provides user-friendly validation messages
- Supports triangles defined by:
  - base and height
  - three side lengths
- Classifies three-sided triangles
- Renders 2D shapes using Java2D
- Renders 3D shapes using projected surface geometry, lighting, and painter-style depth ordering
- Displays calculations and visualizations together in a Swing interface
- Confirms application exit before closing

## Supported Shapes

### Two-Dimensional

- Circle
- Rectangle
- Square
- Triangle

Triangles can be created using either a base and height or three side lengths. Three-sided triangles are validated using the triangle inequality and can be classified according to their sides and angles.

### Three-Dimensional

- Sphere
- Cube
- Cone
- Cylinder
- Torus

The torus additionally validates that its major radius is greater than its minor radius.

## Architecture

The application separates geometry, user interaction, calculations, and rendering into focused components.

```text
src/main/java/
├── geometry/
│   ├── Shape.java
│   ├── twodimensional/
│   │   ├── TwoDimensional.java
│   │   ├── Circle.java
│   │   ├── Rectangle.java
│   │   ├── Square.java
│   │   └── Triangle.java
│   └── threedimensional/
│       ├── ThreeDimensional.java
│       ├── Sphere.java
│       ├── Cube.java
│       ├── Cone.java
│       ├── Cylinder.java
│       └── Torus.java
├── ui/
│   ├── MainWindow.java
│   ├── ShapeDisplay.java
│   ├── calculation/
│   ├── input/
│   ├── workflow/
│   └── rendering/
└── ShapeApp.java
```

### Geometry Model

`Shape` provides the common base for all geometric objects.

`TwoDimensional` and `ThreeDimensional` define separate abstract contracts for area and volume calculations. Concrete shape classes contain their dimensions, mathematical behavior, and domain validation.

### Input and Workflow

Input collection is handled separately from shape construction. The application validates all entered fields before continuing, allowing multiple input errors to be reported together.

Workflow classes coordinate the creation of 2D and 3D shapes without placing those responsibilities in the application entry point or rendering classes.

### Rendering

Rendering is separated from the geometry model.

Individual renderer classes handle each supported shape while shared rendering utilities provide common drawing, dimension-labeling, lighting, projection, and surface-processing behavior.

Three-dimensional shapes are rendered using Java2D rather than external image assets.

## Technologies

- Java 21
- Java Swing
- Java2D
- Maven
- JUnit 5

## Testing

The project includes automated tests for the geometry hierarchy, calculations, validation rules, and supported shapes.

Run the test suite with:

```bash
mvn clean test
```

The current suite contains **38 automated tests**.

## Build

Build and test the application with:

```bash
mvn clean package
```

The packaged JAR is generated under:

```text
target/
```

## Run

After building the project, start the Swing application with:

```bash
mvn exec:java -Dexec.mainClass=ShapeApp
```

The application opens the main geometry-selection window. Choose a shape, enter its dimensions, and the application will calculate and display the result together with its visualization.

No external geometry image assets are required.

## Future Development

Potential future enhancements include a more interactive geometry visualization system with:

- draggable or resizable dimensions
- live area and volume recalculation
- visualization zooming
- richer 3D interaction and rotation

These enhancements would build on the current renderer architecture while preserving the separation between geometry calculations and presentation.

## License

© 2025 James Stevens. All rights reserved.

This source code is provided for educational, evaluation, and portfolio review purposes. Permission is granted to clone and run the code locally for non-commercial review.

No permission is granted to copy, modify, redistribute, or use this code in commercial or production systems without explicit written consent from the author.