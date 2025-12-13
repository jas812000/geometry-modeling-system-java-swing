# Geometry Modeling System (Java Swing)

A Java desktop application that models two-dimensional and three-dimensional geometric shapes using a structured inheritance hierarchy. The system demonstrates abstraction, polymorphism, and enforced contracts through abstract base classes, with concrete implementations calculating areas or volumes. A Swing-based graphical interface coordinates user interaction, runtime object creation, and result visualization.

---

## Project Overview

The Geometry Modeling System is designed to demonstrate disciplined object-oriented design through a hierarchical model of geometric entities. The application supports both two-dimensional and three-dimensional geometry, enforcing correct behavior through abstract base classes while allowing concrete implementations to provide dimension-specific calculations.

User interaction is handled through a Swing-based graphical interface that guides input collection, confirms parameters, executes calculations, and displays results. The system emphasizes architectural clarity, correctness, and maintainability over UI complexity.

---

## Key Concepts Demonstrated

- Object-oriented design using **inheritance**, **abstraction**, and **polymorphism**
- Enforced behavioral contracts via abstract methods
- Clear separation between domain models and UI control logic
- Runtime object creation and dynamic method dispatch
- Event-driven GUI workflows using Java Swing

---

## Geometry Hierarchy

### Base Classes

- **Shape**  
  Defines shared properties common to all geometric entities, including dimensionality.

- **TwoDimensional (abstract)**  
  Requires concrete subclasses to implement `getArea()`.

- **ThreeDimensional (abstract)**  
  Requires concrete subclasses to implement `getVolume()`.

### Implemented Geometry

**Two-Dimensional**
- Circle
- Rectangle
- Square
- Triangle  
  - Supports multiple input strategies (base/height or three sides)
  - Includes triangle classification and angle computation

**Three-Dimensional**
- Sphere
- Cube
- Cone
- Cylinder
- Torus

---

## GUI Application

The primary GUI controller coordinates all user interaction:

1. Users select a geometry type from the main menu.
2. Context-specific dialogs collect required parameters.
3. Confirmation dialogs validate user input.
4. Geometry objects are instantiated dynamically.
5. Area or volume results are calculated and displayed.
6. Corresponding images are displayed for visual reference.

All mathematical computation is delegated to domain classes, preserving separation of concerns.

---

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- Event-Driven Architecture
- Desktop Application Development

---

## How to Run

1. Compile all `.java` files.
2. Run the `ShapeApp` class.
3. When prompted, provide the folder path containing geometry image assets.
4. Use the GUI to construct geometry and view calculated results.

---

## License

© 2025 James Stevens. All rights reserved.

This source code is provided for educational, evaluation, and portfolio review purposes. Permission is granted to clone and run the code locally for non-commercial review.

No permission is granted to copy, modify, redistribute, or use this code in commercial or production systems without explicit written consent from the author.

---
