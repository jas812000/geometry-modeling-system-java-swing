# Java Object-Oriented Shapes GUI

A Java desktop application that demonstrates core object-oriented programming principles through a hierarchical system of two-dimensional and three-dimensional geometric shapes. The application uses a Swing-based graphical interface to construct shapes, collect user input, calculate areas or volumes, and visualize results.

---

## Project Overview

This project models geometric shapes using an inheritance-based class hierarchy rooted in a common `Shape` abstraction. Concrete shape classes implement dimension-specific behavior while sharing a consistent interface. A GUI-driven controller coordinates user interaction, input validation, computation, and visual feedback.

The application supports both 2D and 3D shapes, dynamically constructing objects at runtime based on user selections and displaying calculated results through dialog-driven workflows.

---

## Key Concepts Demonstrated

- Object-oriented design using **inheritance**, **abstraction**, and **polymorphism**
- Separation of concerns between:
  - Domain models (shape classes)
  - Abstract base classes
  - GUI controller logic
- Runtime object creation and method dispatch
- Use of abstract methods (`getArea()`, `getVolume()`) enforced by design
- Swing-based GUI development with event-driven control flow

---

## Shape Hierarchy

### Base Classes
- `Shape`  
  Defines dimensionality common to all shapes.

- `TwoDimensional`  
  Abstract class requiring implementation of `getArea()`.

- `ThreeDimensional`  
  Abstract class requiring implementation of `getVolume()`.

### Implemented Shapes

**Two-Dimensional**
- Circle
- Rectangle
- Square
- Triangle (supports multiple input strategies and classification)

**Three-Dimensional**
- Sphere
- Cube
- Cone
- Cylinder
- Torus

---

## GUI Application

The main GUI (`ShapeApp`) provides:
- A menu-driven interface for shape selection
- Input dialogs tailored to each shape’s required parameters
- Confirmation dialogs prior to computation
- Calculated area or volume output
- Optional image display corresponding to the selected shape
- Controlled application exit with timestamped messaging

All user interaction is handled through Swing components using event listeners and dialog-based workflows.

---

## Technologies Used

- Java
- Java Swing (GUI)
- Object-Oriented Design
- Event-driven programming

---

## How to Run

1. Compile all `.java` files.
2. Run the `ShapeApp` class.
3. When prompted, provide the folder path containing shape images.
4. Interact with the GUI to construct shapes and view results.

---

## License

© 2025 James Stevens. All rights reserved.

This source code is provided for educational, evaluation, and portfolio review purposes. Permission is granted to clone and run the code locally for non-commercial review.

No permission is granted to copy, modify, redistribute, or use this code in commercial or production systems without explicit written consent from the author.

---
